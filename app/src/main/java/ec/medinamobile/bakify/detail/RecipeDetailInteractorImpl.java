package ec.medinamobile.bakify.detail;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by Supertel on 3/7/17.
 */

public class RecipeDetailInteractorImpl implements RecipeDetailInteractor {


    private final Context mContext;
    private final RecipeDetailProviderRepository mProviderRepository;

    public RecipeDetailInteractorImpl(AppCompatActivity context) {
        mContext = context;
        mProviderRepository = new RecipeDetailProviderRepositoryImpl(context);
    }

    @Override
    public void onRetrieveIngredients(int recipeId) {
        mProviderRepository.retrieveIngredient(recipeId);
    }

    @Override
    public void onRetrieveSteps(int recipeId) {
        mProviderRepository.retrieveSteps(recipeId);
    }
}
