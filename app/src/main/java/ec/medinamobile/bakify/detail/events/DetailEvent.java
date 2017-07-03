package ec.medinamobile.bakify.detail.events;

import ec.medinamobile.bakify.entities.Ingredient;
import ec.medinamobile.bakify.entities.Step;

/**
 * Created by Supertel on 3/7/17.
 */

public class DetailEvent {

    public static final int INGREDIENTS_SUCCESS = 10;
    public static final int INGREDIENTS_ERROR = 11;
    public static final int STEPS_SUCCESS = 20;
    public static final int STEPS_ERROR = 21;

    private Ingredient[] ingredients;
    private Step[] steps;
    private int eventType;


    public Ingredient[] getIngredients() {
        return ingredients;
    }

    public void setIngredients(Ingredient[] ingredients) {
        this.ingredients = ingredients;
    }

    public Step[] getSteps() {
        return steps;
    }

    public void setSteps(Step[] steps) {
        this.steps = steps;
    }

    public int getEventType() {
        return eventType;
    }

    public void setEventType(int eventType) {
        this.eventType = eventType;
    }
}
