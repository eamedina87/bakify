package ec.medinamobile.bakify.main.loaders;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;

import ec.medinamobile.bakify.entities.Recipe;

/**
 * Created by Supertel on 30/6/17.
 */

public class RecipesLoaderCallbacks implements LoaderManager.LoaderCallbacks<Recipe[]> {

    private Context mContext;
    private String mUrl;
    private OnRecipeLoadingListener mListener;
    private Recipe[] mRecipes;
    private RecipesAsyncTaskLoader mRecipesLoader;

    public interface OnRecipeLoadingListener{
        void onRecipesStartLoading();
        void onRecipesLoaded(Recipe[] recipes);

    }

    public RecipesLoaderCallbacks(Context context, String url, OnRecipeLoadingListener listener){
        mContext = context;
        mUrl = url;
        mListener = listener;
    }



    @Override
    public Loader<Recipe[]> onCreateLoader(int id, Bundle args) {
        mListener.onRecipesStartLoading();
        if (mRecipesLoader==null){
            mRecipesLoader = new RecipesAsyncTaskLoader(mContext, mUrl);
        }
        return mRecipesLoader;
    }

    @Override
    public void onLoadFinished(Loader<Recipe[]> loader, Recipe[] data) {
        mListener.onRecipesLoaded(data);
    }

    @Override
    public void onLoaderReset(Loader<Recipe[]> loader) {
        mListener = null;
    }
}
