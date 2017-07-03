package ec.medinamobile.bakify.detail.loaders;

import ec.medinamobile.bakify.entities.Step;

/**
 * Created by Supertel on 3/7/17.
 */

public interface OnStepCursorLoaderListener {
    void onStepsLoaded(Step[] steps);
}
