package ec.medinamobile.bakify.utils;

import android.content.ContentValues;
import android.database.Cursor;

import net.simonvt.schematic.annotation.AutoIncrement;
import net.simonvt.schematic.annotation.DataType;
import net.simonvt.schematic.annotation.NotNull;
import net.simonvt.schematic.annotation.PrimaryKey;

import ec.medinamobile.bakify.database.RecipesColumns;
import ec.medinamobile.bakify.entities.Recipe;

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
}
