package ec.medinamobile.bakify.detail.ui;

import android.content.Intent;
import android.os.Bundle;
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
//(TODO) Test on configuration changes
//(TODO) Add Content Description
//(TODO) Make tablet compatible
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

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_pane);
        ButterKnife.bind(this);
        getIntentData();
        setupFragments();
        setupPresenter();
        getIngredientsFromDB();
        getStepsFromDB();
        setupActionBar();
    }

    private void setupFragments() {
        ingredientsStepsFragment = new IngredientsStepsFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(Constants.INTENT_EXTRA_RECIPE, mRecipe);
        ingredientsStepsFragment.setArguments(bundle);
        getSupportFragmentManager().beginTransaction().
                add(R.id.detail_pane_ingredients_steps, ingredientsStepsFragment).
                commit();
        if (stepDetailContainer!=null){
            mTwoPane = true;
            stepVideoFragment = new StepVideoFragment();
            //Bundle videoBundle = new Bundle();
            //videoBundle.putParcelable(Constants.INTENT_EXTRA_STEP, mRecipe.getSteps()[0]);
            //stepVideoFragment.setArguments(videoBundle);
            getSupportFragmentManager().beginTransaction().
                    add(R.id.detail_pane_step_detail, stepVideoFragment).
                    commit();

        } else {
            mTwoPane = false;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.onDestroy();
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
                    addToBackStack(Constants.INTENT_EXTRA_STEP).
                    commit();
        }
    }


    @Override
    public void onStepClicked(Step step) {
        mPresenter.onStepSelected(step);
    }
}
