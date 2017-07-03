package ec.medinamobile.bakify.detail.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import ec.medinamobile.bakify.R;
import ec.medinamobile.bakify.detail.RecipeDetailPresenter;
import ec.medinamobile.bakify.detail.RecipeDetailPresenterImpl;
import ec.medinamobile.bakify.detail.adapters.IngredientsAdapter;
import ec.medinamobile.bakify.detail.adapters.OnStepItemClickListener;
import ec.medinamobile.bakify.detail.adapters.StepsAdapter;
import ec.medinamobile.bakify.entities.Ingredient;
import ec.medinamobile.bakify.entities.Recipe;
import ec.medinamobile.bakify.entities.Step;
import ec.medinamobile.bakify.utils.Constants;
import ec.medinamobile.bakify.video.ui.StepVideoActivity;

/**
 * Created by Erick on 2/7/17.
 */
//(TODO) Test on configuration changes
//(TODO) Add Content Description
//(TODO) Make tablet compatible
public class RecipeDetailActivity extends AppCompatActivity implements RecipeDetailView, OnStepItemClickListener{


    @BindView(R.id.detail_ingredients_list)
    RecyclerView ingredientsList;
    @BindView(R.id.detail_steps_list)
    RecyclerView stepsList;
    @BindView(R.id.detail_ingredients_title)
    TextView ingredientsTitle;
    @BindView(R.id.detail_steps_title)
    TextView stepsTitle;

    private Recipe mRecipe;
    RecipeDetailPresenter mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);
        getIntentData();
        setupPresenter();
        getIngredientsFromDB();
        getStepsFromDB();
        setupActionBar();
    }

    private void setupActionBar() {
        getSupportActionBar().setTitle(mRecipe.getName());
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void setupPresenter() {
        mPresenter = new RecipeDetailPresenterImpl(this, this);
        mPresenter.onCreate();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.onDestroy();
    }

    private void getStepsFromDB() {
        mPresenter.onRetrieveSteps(mRecipe.getId());
    }

    private void getIngredientsFromDB() {
        mPresenter.onRetrieveIngredients(mRecipe.getId());
    }


    private void getIntentData() {
        Intent intent = getIntent();
        if (intent!=null && intent.getExtras()!=null && intent.hasExtra(Constants.INTENT_EXTRA_RECIPE)){
            mRecipe = intent.getExtras().getParcelable(Constants.INTENT_EXTRA_RECIPE);
        }
    }

    private void setupStepsRecyclerView() {
        StepsAdapter stepsAdapter = new StepsAdapter(mRecipe.getSteps(), this);
        stepsList.setAdapter(stepsAdapter);
        RecyclerView.LayoutManager ingredientsManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        stepsList.setLayoutManager(ingredientsManager);
    }

    private void setupIngredientsRecyclerView() {
        IngredientsAdapter ingredientsAdapter = new IngredientsAdapter(mRecipe.getIngredients());
        ingredientsList.setAdapter(ingredientsAdapter);
        RecyclerView.LayoutManager ingredientsManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        ingredientsList.setLayoutManager(ingredientsManager);
    }

    @Override
    public void onIngredientsLoaded(Ingredient[] ingredients) {
        if (ingredients!=null && ingredients.length>0){
            mRecipe.setIngredients(ingredients);
            ingredientsTitle.setText(String.format(getString(R.string.ingredients_title),ingredients.length));
            setupIngredientsRecyclerView();
        } else {
            //Show EmptyDisplay
        }
    }

    @Override
    public void onStepsLoaded(Step[] steps) {
        if (steps!=null && steps.length>0){
            mRecipe.setSteps(steps);
            stepsTitle.setText(String.format(getString(R.string.steps_title),steps.length));
            setupStepsRecyclerView();
        } else {
            //Show EmptyDisplay
        }
    }

    @Override
    public void showStepVideo(Step step) {
        Intent intent = new Intent(this, StepVideoActivity.class);
        Bundle bundle = new Bundle();
        bundle.putParcelable(Constants.INTENT_EXTRA_STEP, step);
        intent.putExtras(bundle);
        startActivity(intent);
    }


    @Override
    public void onStepClicked(Step step) {
        mPresenter.onStepSelected(step);
    }
}
