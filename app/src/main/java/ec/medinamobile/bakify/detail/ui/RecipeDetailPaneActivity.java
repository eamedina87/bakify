package ec.medinamobile.bakify.detail.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.FrameLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import ec.medinamobile.bakify.R;
import ec.medinamobile.bakify.detail.RecipeDetailPresenter;
import ec.medinamobile.bakify.detail.RecipeDetailPresenterImpl;
import ec.medinamobile.bakify.entities.Ingredient;
import ec.medinamobile.bakify.entities.Recipe;
import ec.medinamobile.bakify.entities.Step;
import ec.medinamobile.bakify.utils.Constants;
import ec.medinamobile.bakify.video.ui.StepVideoActivity;
import ec.medinamobile.bakify.video.ui.StepVideoFragment;

/**
 * Created by Erick on 2/7/17.
 */
//(TODO) Test on configuration changes with Exaplayer
//(TODO) Add Content Description
public class RecipeDetailPaneActivity extends AppCompatActivity implements RecipeDetailView, OnIngredientsStepsFragmentCallbacks {

    @BindView(R.id.detail_pane_ingredients_steps)
    FrameLayout ingredientsStepsContainer;
    @Nullable
    @BindView(R.id.detail_pane_step_detail)
    FrameLayout stepDetailContainer;
    private Recipe mRecipe;
    RecipeDetailPresenter mPresenter;
    IngredientsStepsFragment ingredientsStepsFragment;
    private boolean mTwoPane;
    private StepVideoFragment stepVideoFragment;
    private Step mSelectedStep;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_pane);
        ButterKnife.bind(this);
        getIntentData();
        setupPresenter();
        if (savedInstanceState==null){
            setupFragments();
            getIngredientsFromDB();
            getStepsFromDB();
        }
        setupActionBar();
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(Constants.BUNDLE_TWO_PANE, mTwoPane);
        if (mSelectedStep!=null){
            outState.putParcelable(Constants.BUNDLE_STEP, mSelectedStep);
        }
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if (savedInstanceState!=null){
            mTwoPane = savedInstanceState.getBoolean(Constants.BUNDLE_TWO_PANE);
            if (savedInstanceState.get(Constants.BUNDLE_STEP)!=null){
                mSelectedStep = savedInstanceState.getParcelable(Constants.BUNDLE_STEP);
            }
            if (mTwoPane) setupVideoFragment();
        }

    }

    private void setupFragments() {
        ingredientsStepsFragment = new IngredientsStepsFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(Constants.INTENT_EXTRA_RECIPE, mRecipe);
        ingredientsStepsFragment.setArguments(bundle);
        getSupportFragmentManager().beginTransaction().
                add(R.id.detail_pane_ingredients_steps, ingredientsStepsFragment).
                commit();
        setupVideoFragment();
    }

    private void setupVideoFragment() {
        if (stepDetailContainer!=null){
            mTwoPane = true;
            stepVideoFragment = new StepVideoFragment();
            if (mSelectedStep!=null) {
                Bundle args = new Bundle();
                args.putParcelable(Constants.BUNDLE_STEP, mSelectedStep);
                stepVideoFragment.setArguments(args);
            }
            getSupportFragmentManager().beginTransaction().
                    replace(R.id.detail_pane_step_detail, stepVideoFragment).
                    commit();


        } else {
            mTwoPane = false;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.onDestroy();
        //if (ingredientsStepsFragment!=null){
        //    getSupportFragmentManager().beginTransaction().remove(ingredientsStepsFragment);
        //}
    }


    private void getIntentData() {
        Intent intent = getIntent();
        if (intent!=null && intent.getExtras()!=null && intent.hasExtra(Constants.INTENT_EXTRA_RECIPE)){
            mRecipe = intent.getExtras().getParcelable(Constants.INTENT_EXTRA_RECIPE);
        }
    }

    private void setupPresenter() {
        mPresenter = new RecipeDetailPresenterImpl(this, this);
        mPresenter.onCreate();
    }

    private void setupActionBar() {
        getSupportActionBar().setTitle(mRecipe.getName());
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void getIngredientsFromDB() {
        mPresenter.onRetrieveIngredients(mRecipe.getId());
    }

    private void getStepsFromDB() {
        mPresenter.onRetrieveSteps(mRecipe.getId());
    }


    @Override
    public void onIngredientsLoaded(Ingredient[] ingredients) {
        ingredientsStepsFragment.setIngredients(ingredients);
    }

    @Override
    public void onStepsLoaded(Step[] steps) {
        ingredientsStepsFragment.setSteps(steps);
        if (mTwoPane){
            mSelectedStep = steps[0];
            stepVideoFragment.setStep(steps[0]);

        }
    }

    @Override
    public void showStepVideo(Step step) {
        if (mTwoPane) {
            /*Intent intent = new Intent(this, StepVideoActivity.class);
            Bundle bundle = new Bundle();
            bundle.putParcelable(Constants.INTENT_EXTRA_STEP, step);
            intent.putExtras(bundle);
            startActivity(intent);*/
            stepVideoFragment.setStep(step);
        } else {
            StepVideoFragment stepFragment = new StepVideoFragment();
            Bundle videoBundle = new Bundle();
            videoBundle.putParcelable(Constants.INTENT_EXTRA_STEP, step);
            stepFragment.setArguments(videoBundle);
            getSupportFragmentManager().beginTransaction().
                    replace(R.id.detail_pane_ingredients_steps, stepFragment).
                    addToBackStack(Constants.BUNDLE_STEP).
                    commit();
        }
    }


    @Override
    public void onStepClicked(Step step) {
        mSelectedStep = step;
        mPresenter.onStepSelected(step);
    }
}
