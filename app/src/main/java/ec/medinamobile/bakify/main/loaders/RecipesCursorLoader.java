package ec.medinamobile.bakify.main.loaders;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;

import ec.medinamobile.bakify.database.BakifyProvider;

/**
 * Created by Supertel on 1/7/17.
 */

public class RecipesCursorLoader implements LoaderManager.LoaderCallbacks<Cursor> {

    private final Context mContext;
    private final OnRecipeCursorLoadingListener mCursorListener;
    private OnRecipeCursorLoadingListener cursorLoadingListener;

    public RecipesCursorLoader(Context context, OnRecipeCursorLoadingListener listener){
        mContext = context;
        mCursorListener = listener;
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        mCursorListener.onRecipeCursorStartLoading();
        Uri mUri = BakifyProvider.Recipes.RECIPES_CONTENT_URI;
        return new CursorLoader(mContext, mUri, null, null, null, null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        mCursorListener.onRecipeCursorLoaded(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }
}
