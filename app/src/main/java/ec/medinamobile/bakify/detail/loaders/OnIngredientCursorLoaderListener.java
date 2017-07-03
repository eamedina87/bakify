package ec.medinamobile.bakify.detail.loaders;

import ec.medinamobile.bakify.entities.Ingredient;

/**
 * Created by Supertel on 3/7/17.
 */

public interface OnIngredientCursorLoaderListener {
    void onIngredientsLoaded(Ingredient[] ingredients);
}
