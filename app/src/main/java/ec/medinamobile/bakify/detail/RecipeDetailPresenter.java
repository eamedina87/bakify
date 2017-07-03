package ec.medinamobile.bakify.detail;

import ec.medinamobile.bakify.detail.events.DetailEvent;
import ec.medinamobile.bakify.entities.Step;

/**
 * Created by Erick on 3/7/17.
 */

public interface RecipeDetailPresenter {
    void onCreate();
    void onDestroy();
    void onStepSelected(Step step);
    void onRetrieveIngredients(int recipeId);
    void onRetrieveSteps(int recipeId);
    void onEventMainThread(DetailEvent event);
}
