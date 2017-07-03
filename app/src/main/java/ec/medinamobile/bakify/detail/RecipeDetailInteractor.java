package ec.medinamobile.bakify.detail;

/**
 * Created by Supertel on 3/7/17.
 */

public interface RecipeDetailInteractor {
    void onRetrieveIngredients(int recipeId);
    void onRetrieveSteps(int recipeId);
}
