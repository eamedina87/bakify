package ec.medinamobile.bakify.detail.loaders;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;

import ec.medinamobile.bakify.database.BakifyProvider;
import ec.medinamobile.bakify.database.IngredientsColumns;
import ec.medinamobile.bakify.entities.Ingredient;
import ec.medinamobile.bakify.utils.Constants;
import ec.medinamobile.bakify.utils.DatabaseUtils;
import ec.medinamobile.bakify.utils.JsonUtils;

/**
 * Created by Erick on 3/7/17.
 */

public class IngredientsCursorLoaderCallbacks implements LoaderManager.LoaderCallbacks<Cursor> {

    private Context mContext;
    private OnIngredientCursorLoaderListener mListener;

    public IngredientsCursorLoaderCallbacks(Context context, OnIngredientCursorLoaderListener listener){
        mListener = listener;
        mContext = context;
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        Uri mUri = BakifyProvider.Ingredients.INGREDIENTS_CONTENT_URI;
        int recipeId = -1;
        if (args!=null){
            recipeId = args.getInt(IngredientsColumns.RECIPE_ID);
        }
        return new CursorLoader(mContext,
                mUri,
                null,
                DatabaseUtils.getIngredientsSelection(),
                DatabaseUtils.getIngredientsSelectionArgs(recipeId),
                null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        Ingredient[] ingredients = null;
        if (data!=null) ingredients = DatabaseUtils.getIngredientArrayFromCursor(data);
        mListener.onIngredientsLoaded(ingredients);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mListener = null;
        mContext = null;
    }
}
