package ec.medinamobile.bakify.utils;

import android.content.ContentValues;
import android.database.Cursor;

import net.simonvt.schematic.annotation.AutoIncrement;
import net.simonvt.schematic.annotation.DataType;
import net.simonvt.schematic.annotation.NotNull;
import net.simonvt.schematic.annotation.PrimaryKey;

import java.util.ArrayList;

import ec.medinamobile.bakify.database.IngredientsColumns;
import ec.medinamobile.bakify.database.RecipesColumns;
import ec.medinamobile.bakify.database.StepsColumns;
import ec.medinamobile.bakify.entities.Ingredient;
import ec.medinamobile.bakify.entities.Recipe;
import ec.medinamobile.bakify.entities.Step;

import static net.simonvt.schematic.annotation.DataType.Type.INTEGER;
import static net.simonvt.schematic.annotation.DataType.Type.TEXT;

/**
 * Created by Supertel on 1/7/17.
 */

public class DatabaseUtils {
    public static Recipe[] getRecipeArrayFromCursor(Cursor mCursor) {
        if (mCursor==null || mCursor.getCount()==0) return null;
        Recipe[] mRecipes = new Recipe[mCursor.getCount()];
        while (mCursor.moveToNext()){
            mRecipes[mCursor.getPosition()] = getRecipeFromCursor(mCursor);
        }
        mCursor.close();
        return mRecipes;
    }

    private static Recipe getRecipeFromCursor(Cursor mCursor) {
        Recipe recipe = new Recipe();
        recipe.setId(mCursor.getInt(mCursor.getColumnIndex(RecipesColumns.SERVER_ID)));
        recipe.setName(mCursor.getString(mCursor.getColumnIndex(RecipesColumns.NAME)));
        recipe.setServings(mCursor.getInt(mCursor.getColumnIndex(RecipesColumns.SERVINGS)));
        recipe.setImage(mCursor.getString(mCursor.getColumnIndex(RecipesColumns.IMAGE)));
        return recipe;
    }

    public static Ingredient[] getIngredientArrayFromCursor(Cursor mCursor) {
        if (mCursor==null || mCursor.getCount()==0) return null;
        Ingredient[] mIngredients = new Ingredient[mCursor.getCount()];
        while (mCursor.moveToNext()){
            mIngredients[mCursor.getPosition()] = getIngredientFromCursor(mCursor);
        }
        mCursor.close();
        return mIngredients;
    }

    private static Ingredient getIngredientFromCursor(Cursor mCursor) {
        Ingredient ingredient = new Ingredient();
        ingredient.setIngredient(mCursor.getString(mCursor.getColumnIndex(IngredientsColumns.INGREDIENT)));
        ingredient.setMeasure(mCursor.getString(mCursor.getColumnIndex(IngredientsColumns.MEASURE)));
        ingredient.setQuantity(mCursor.getFloat(mCursor.getColumnIndex(IngredientsColumns.QUANTITY)));
        return ingredient;
    }

    public static Step[] getStepArrayFromCursor(Cursor mCursor) {
        if (mCursor==null || mCursor.getCount()==0) return null;
        Step[] mSteps = new Step[mCursor.getCount()];
        while (mCursor.moveToNext()){
            mSteps[mCursor.getPosition()] = getStepFromCursor(mCursor);
        }
        mCursor.close();
        return mSteps;
    }

    private static Step getStepFromCursor(Cursor mCursor) {
        Step step = new Step();
        step.setThumbnailURL(mCursor.getString(mCursor.getColumnIndex(StepsColumns.THUBMNAIL_URL)));
        step.setVideoURL(mCursor.getString(mCursor.getColumnIndex(StepsColumns.VIDEO_URL)));
        step.setDescription(mCursor.getString(mCursor.getColumnIndex(StepsColumns.DESCRIPTION)));
        step.setShortDescription(mCursor.getString(mCursor.getColumnIndex(StepsColumns.SHORT_DESCRIPTION)));
        step.setId(mCursor.getInt(mCursor.getColumnIndex(StepsColumns.SERVER_ID)));
        return step;
    }

    public static ContentValues[] getRecipeContentValuesArray(Recipe[] recipes) {
        ContentValues[] valuesArray = new ContentValues[recipes.length];
        for (int i=0; i<recipes.length;i++){
            Recipe recipe = recipes[i];
            ContentValues values = new ContentValues();
            values.put(RecipesColumns.SERVER_ID, recipe.getId());
            values.put(RecipesColumns.IMAGE, recipe.getImage());
            values.put(RecipesColumns.NAME, recipe.getName());
            values.put(RecipesColumns.SERVINGS, recipe.getServings());
            valuesArray[i] = values;
        }
        return valuesArray;
    }

    public static ContentValues[] getIngredientsContentValuesArray(Recipe[] recipes) {
        ArrayList<Ingredient> mIngredientsList = new ArrayList<>();
        for (Recipe recipe:recipes){
            Ingredient[] ingredients = recipe.getIngredients();
            for (Ingredient ingredient:ingredients){
                ingredient.setRecipeId(recipe.getId());
                mIngredientsList.add(ingredient);
            }
        }

        ContentValues[] contentValues = new ContentValues[mIngredientsList.size()];
        for (int i=0; i<mIngredientsList.size(); i++){
            Ingredient ingredient = mIngredientsList.get(i);
            ContentValues values = new ContentValues();
            values.put(IngredientsColumns.RECIPE_ID, ingredient.getRecipeId());
            values.put(IngredientsColumns.INGREDIENT, ingredient.getIngredient());
            values.put(IngredientsColumns.MEASURE, ingredient.getMeasure());
            values.put(IngredientsColumns.QUANTITY, ingredient.getQuantity());
            contentValues[i]= values;
        }

        return contentValues;
    }

    public static ContentValues[] getStepsContentValuesArray(Recipe[] recipes) {
        ArrayList<Step> mStepsList = new ArrayList<>();
        for (Recipe recipe:recipes){
            Step[] steps = recipe.getSteps();
            for (Step step:steps){
                step.setRecipeId(recipe.getId());
                mStepsList.add(step);
            }
        }

        ContentValues[] contentValues = new ContentValues[mStepsList.size()];
        for (int i=0; i<mStepsList.size(); i++){
            Step step = mStepsList.get(i);
            ContentValues values = new ContentValues();
            values.put(StepsColumns.RECIPE_ID, step.getRecipeId());
            values.put(StepsColumns.DESCRIPTION, step.getDescription());
            values.put(StepsColumns.SHORT_DESCRIPTION, step.getShortDescription());
            values.put(StepsColumns.SERVER_ID, step.getServerId());
            values.put(StepsColumns.THUBMNAIL_URL, step.getThumbnailURL());
            values.put(StepsColumns.VIDEO_URL, step.getVideoURL());
            contentValues[i]= values;
        }

        return contentValues;

    }

    public static String getIngredientsSelection(){
        return IngredientsColumns.RECIPE_ID+"=?";
    }

    public static String[] getIngredientsSelectionArgs(int recipeId) {
        return new String[]{String.valueOf(recipeId)};
    }

    public static String getStepsSelection() {
        return StepsColumns.RECIPE_ID+"=?";
    }

    public static String[] getStepsSelectionArgs(int recipeId) {
        return new String[]{String.valueOf(recipeId)};
    }


}
