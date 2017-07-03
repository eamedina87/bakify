package ec.medinamobile.bakify.detail;

/**
 * Created by Supertel on 3/7/17.
 */

public interface RecipeDetailProviderRepository {
    void retrieveIngredient(int recipeId);
    void retrieveSteps(int recipeId);

}
