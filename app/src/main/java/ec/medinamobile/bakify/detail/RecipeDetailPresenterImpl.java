package ec.medinamobile.bakify.detail;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;

import org.greenrobot.eventbus.Subscribe;

import ec.medinamobile.bakify.detail.events.DetailEvent;
import ec.medinamobile.bakify.detail.ui.RecipeDetailView;
import ec.medinamobile.bakify.entities.Ingredient;
import ec.medinamobile.bakify.entities.Step;
import ec.medinamobile.bakify.libs.EventBusImpl;

/**
 * Created by Erick on 3/7/17.
 */

public class RecipeDetailPresenterImpl implements RecipeDetailPresenter {

    private final EventBusImpl mEventBus;
    private final RecipeDetailInteractorImpl mInteractor;
    private RecipeDetailView mView;
    private Context mContext;

    public RecipeDetailPresenterImpl(AppCompatActivity context, RecipeDetailView view){
        mContext = context;
        mView = view;
        mEventBus = EventBusImpl.getInstance();
        mInteractor = new RecipeDetailInteractorImpl(context);
    }

    @Override
    public void onCreate() {
        mEventBus.register(this);
    }

    @Override
    public void onDestroy() {
        mEventBus.unregister(this);
        mView = null;
    }

    @Override
    public void onStepSelected(Step step) {
        mView.showStepVideo(step);
    }

    @Override
    public void onRetrieveIngredients(int recipeId) {
        mInteractor.onRetrieveIngredients(recipeId);
    }

    @Override
    public void onRetrieveSteps(int recipeId) {
        mInteractor.onRetrieveSteps(recipeId);
    }

    @Subscribe
    @Override
    public void onEventMainThread(DetailEvent event) {
        switch (event.getEventType()){
            case DetailEvent.INGREDIENTS_SUCCESS:{
                onIngredientsSuccess(event.getIngredients());
                break;
            }
            case DetailEvent.STEPS_SUCCESS:{
                onStepsSuccess(event.getSteps());
                break;
            }
        }

    }

    private void onIngredientsSuccess(Ingredient[] ingredients) {
        mView.onIngredientsLoaded(ingredients);
    }

    private void onStepsSuccess(Step[] steps) {
        mView.onStepsLoaded(steps);
    }


}
