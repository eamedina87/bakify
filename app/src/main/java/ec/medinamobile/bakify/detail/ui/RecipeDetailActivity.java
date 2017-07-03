package ec.medinamobile.bakify.detail.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

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

/**
 * Created by Erick on 2/7/17.
 */

public class RecipeDetailActivity extends AppCompatActivity implements RecipeDetailView, OnStepItemClickListener{

    @BindView(R.id.detail_ingredients_list)
    RecyclerView ingredientsList;
    @BindView(R.id.detail_steps_list)
    RecyclerView stepsList;
    private Recipe mRecipe;
    RecipeDetailPresenter presenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);
        getIntentData();
        setupPresenter();
        getIngredientsFromDB();
        getStepsFromDB();
    }

    private void setupPresenter() {
        presenter = new RecipeDetailPresenterImpl(this, this);
        presenter.onCreate();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.onDestroy();
    }

    private void getStepsFromDB() {
        presenter.onRetrieveSteps(mRecipe.getId());
    }

    private void getIngredientsFromDB() {
        presenter.onRetrieveIngredients(mRecipe.getId());
    }


    private void getIntentData() {
        Intent intent = getIntent();
        if (intent!=null && intent.getExtras()!=null && intent.hasExtra(Constants.INTENT_EXTRA_RECIPE)){
            mRecipe = intent.getExtras().getParcelable(Constants.INTENT_EXTRA_RECIPE);
        }
    }

    private void setupStepsRecyclerView() {
        StepsAdapter stepsAdapter = new StepsAdapter(mRecipe.getSteps());
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
    public void enableViews() {

    }

    @Override
    public void disableViews() {

    }

    @Override
    public void onIngredientsLoaded(Ingredient[] ingredients) {
        if (ingredients!=null && ingredients.length>0){
            mRecipe.setIngredients(ingredients);
            setupIngredientsRecyclerView();
        } else {
            //Show EmptyDisplay
        }
    }

    @Override
    public void onStepsLoaded(Step[] steps) {
        if (steps!=null && steps.length>0){
            mRecipe.setSteps(steps);
            setupStepsRecyclerView();
        } else {
            //Show EmptyDisplay
        }

    }

    @Override
    public void showStepVideo(Step step) {

    }


    @Override
    public void onStepClicked(Step step) {
        Toast.makeText(this, step.getId(), Toast.LENGTH_SHORT).show();
    }
}
