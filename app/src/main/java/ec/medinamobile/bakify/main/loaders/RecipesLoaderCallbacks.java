package ec.medinamobile.bakify.main.loaders;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;

import ec.medinamobile.bakify.entities.Recipe;

/**
 * Created by Erick on 30/6/17.
 */

public class RecipesLoaderCallbacks implements LoaderManager.LoaderCallbacks<Recipe[]> {

    private Context mContext;
    private String mUrl;
    private OnRecipeLoadingListener mListener;
    private Recipe[] mRecipes;
    private RecipesAsyncTaskLoader mRecipesLoader;

    public RecipesLoaderCallbacks(Context context, String url, OnRecipeLoadingListener listener){
        mContext = context;
        mUrl = url;
        mListener = listener;
    }

    @Override
    public Loader<Recipe[]> onCreateLoader(int id, Bundle args) {
        mListener.onRecipesStartLoading();
        return new RecipesAsyncTaskLoader(mContext, mUrl);
    }

    @Override
    public void onLoadFinished(Loader<Recipe[]> loader, Recipe[] data) {
        mListener.onRecipesLoaded(data);
        if (data!=null){
            mRecipes = data;
        }
    }

    @Override
    public void onLoaderReset(Loader<Recipe[]> loader) {
        mListener = null;
    }
}
