package ec.medinamobile.bakify.main.loaders;

import ec.medinamobile.bakify.entities.Recipe;

/**
 * Created by Erick on 1/7/17.
 */

public interface OnRecipeLoadingListener{
    void onRecipesStartLoading();
    void onRecipesLoaded(Recipe[] recipes);

}
