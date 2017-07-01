package ec.medinamobile.bakify.main.loaders;


import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

import java.util.ArrayList;
import java.util.Arrays;

import ec.medinamobile.bakify.entities.Recipe;
import ec.medinamobile.bakify.utils.JsonUtils;
import ec.medinamobile.bakify.utils.NetworkUtils;

/**
 * Created by Erick on 30/6/17.
 */

public class RecipesAsyncTaskLoader extends AsyncTaskLoader<Recipe[]> {

    private final String mUrl;
    private Recipe[] recipes;

    public RecipesAsyncTaskLoader(Context context, String url) {
        super(context);
        this.mUrl = url;
    }

    @Override
    protected void onStartLoading() {
        super.onStartLoading();
        if (recipes==null){
            forceLoad();
        } else {
            deliverResult(recipes);
        }
    }

    @Override
    public Recipe[] loadInBackground() {
        String recipesJson = NetworkUtils.getRecipesFromServer(mUrl);
        recipes = JsonUtils.getRecipesFromJson(recipesJson);
        return recipes;
    }

}
