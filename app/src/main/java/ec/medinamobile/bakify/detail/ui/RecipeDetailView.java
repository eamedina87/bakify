package ec.medinamobile.bakify.detail.ui;

import ec.medinamobile.bakify.entities.Ingredient;
import ec.medinamobile.bakify.entities.Step;

/**
 * Created by Supertel on 3/7/17.
 */

public interface RecipeDetailView {
    void enableViews();
    void disableViews();
    void onIngredientsLoaded(Ingredient[] ingredients);
    void onStepsLoaded(Step[] steps);
    void showStepVideo(Step step);
}
