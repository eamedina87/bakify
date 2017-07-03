package ec.medinamobile.bakify.detail;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import ec.medinamobile.bakify.database.IngredientsColumns;
import ec.medinamobile.bakify.database.StepsColumns;
import ec.medinamobile.bakify.detail.events.DetailEvent;
import ec.medinamobile.bakify.detail.loaders.IngredientsCursorLoaderCallbacks;
import ec.medinamobile.bakify.detail.loaders.OnIngredientCursorLoaderListener;
import ec.medinamobile.bakify.detail.loaders.OnStepCursorLoaderListener;
import ec.medinamobile.bakify.detail.loaders.StepsCursorLoaderCallbacks;
import ec.medinamobile.bakify.entities.Ingredient;
import ec.medinamobile.bakify.entities.Step;
import ec.medinamobile.bakify.libs.EventBusImpl;
import ec.medinamobile.bakify.utils.Constants;

/**
 * Created by Erick on 3/7/17.
 */

public class RecipeDetailProviderRepositoryImpl implements RecipeDetailProviderRepository, OnIngredientCursorLoaderListener, OnStepCursorLoaderListener {

    private final EventBusImpl mEventBus;
    private AppCompatActivity mContext;

    public RecipeDetailProviderRepositoryImpl(AppCompatActivity context) {
        mContext = context;
        mEventBus = EventBusImpl.getInstance();
    }

    @Override
    public void retrieveIngredient(int recipeId) {
        IngredientsCursorLoaderCallbacks ingredientsCallbacks = new IngredientsCursorLoaderCallbacks(mContext, this);
        Bundle bundle = new Bundle();
        bundle.putInt(IngredientsColumns.RECIPE_ID, recipeId);
        mContext.getSupportLoaderManager().initLoader(
                Constants.INGREDIENTS_CURSOR_LOADER_ID,
                bundle,
                ingredientsCallbacks
        );
    }

    @Override
    public void retrieveSteps(int recipeId) {
        StepsCursorLoaderCallbacks stepsCallbacks = new StepsCursorLoaderCallbacks(mContext, this);
        Bundle bundle = new Bundle();
        bundle.putInt(StepsColumns.RECIPE_ID, recipeId);
        mContext.getSupportLoaderManager().initLoader(
                Constants.STEPS_CURSOR_LOADER_ID,
                bundle,
                stepsCallbacks
        );
    }

    @Override
    public void onIngredientsLoaded(Ingredient[] ingredients) {
        Log.d(this.getClass().getName(), "IngredientsArraySize: " + ingredients!=null?String.valueOf(ingredients.length):"null");
        DetailEvent mEvent = new DetailEvent();
        mEvent.setEventType(DetailEvent.INGREDIENTS_SUCCESS);
        mEvent.setIngredients(ingredients);
        mEventBus.post(mEvent);
    }

    @Override
    public void onStepsLoaded(Step[] steps) {
        Log.d(this.getClass().getName(), "StepsArraySize: " + steps!=null?String.valueOf(steps.length):"null");
        DetailEvent mEvent = new DetailEvent();
        mEvent.setEventType(DetailEvent.STEPS_SUCCESS);
        mEvent.setSteps(steps);
        mEventBus.post(mEvent);
    }

}
