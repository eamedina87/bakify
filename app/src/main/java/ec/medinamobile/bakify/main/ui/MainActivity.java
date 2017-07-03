package ec.medinamobile.bakify.main.ui;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.support.v7.app.AppCompatActivity;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import ec.medinamobile.bakify.R;
import ec.medinamobile.bakify.database.BakifyProvider;
import ec.medinamobile.bakify.detail.ui.RecipeDetailActivity;
import ec.medinamobile.bakify.entities.Recipe;
import ec.medinamobile.bakify.main.adapters.OnRecipeItemClickListener;
import ec.medinamobile.bakify.main.adapters.RecipesAdapter;
import ec.medinamobile.bakify.main.loaders.OnRecipeCursorLoadingListener;
import ec.medinamobile.bakify.main.loaders.OnRecipeLoadingListener;
import ec.medinamobile.bakify.main.loaders.RecipesCursorLoader;
import ec.medinamobile.bakify.main.loaders.RecipesLoaderCallbacks;
import ec.medinamobile.bakify.utils.Constants;
import ec.medinamobile.bakify.utils.DatabaseUtils;
import ec.medinamobile.bakify.utils.JsonUtils;
import ec.medinamobile.bakify.utils.NetworkUtils;
import ec.medinamobile.bakify.utils.PreferenceUtils;


/* Activity displays a Grid ReyclerView with the recipes name and image if available
 * If no information is in the database, it downloads information from the URL and saves it to the DB via
 * the Content Provider
 */

//(TODO) Apply MVP Pattern
//(TODO) Test on orientation changes
//(TODO) Add contentDescription
public class MainActivity extends AppCompatActivity implements OnRecipeLoadingListener, OnRecipeItemClickListener, OnRecipeCursorLoadingListener {

    @BindView(R.id.contentLayout)
    FrameLayout contentLayout;
    @BindView(R.id.main_recyclerview)
    RecyclerView recipesList;
    @BindView(R.id.main_empty_display)
    TextView emptyDisplay;
    @BindView(R.id.progressbar)
    ProgressBar progressBar;

    private RecipesAdapter mAdapter;
    private RecipesLoaderCallbacks mRecipesLoaderCallbacks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_main);
        ButterKnife.bind(this);
        setupRecyclerView();
        setupRecipes();
    }


    private void setupRecipes() {
        if (PreferenceUtils.areRecipesInDatabase(this)){
            loadRecipesFromDB();
        } else {
            loadRecipesFromServer();
        }
    }

    private void loadRecipesFromServer() {

        String jsonString = JsonUtils.readJsonFromAssets(this);
        Recipe[] recipes = JsonUtils.getRecipesFromJson(jsonString);
        onRecipesLoaded(recipes);
        /*
        if (NetworkUtils.isInternetAvailable(this)){
            showProgress();
            hideAllButProgressBar();
            mRecipesLoaderCallbacks =
                    new RecipesLoaderCallbacks(this, NetworkUtils.JSON_RECIPES_SERVER_URL, this);
            getSupportLoaderManager().initLoader(
                    RecipesLoaderCallbacks.RECIPES_LOADER_ID,
                    null,
                    mRecipesLoaderCallbacks);
        } else {
            showInternetUnavailableSnackbar();

        }
        */
    }



    private void reloadRecipesFromServer() {
        if (NetworkUtils.isInternetAvailable(this)){
            showProgress();
            hideAllButProgressBar();
            getSupportLoaderManager().restartLoader(
                    Constants.RECIPES_LOADER_ID,
                    null,
                    mRecipesLoaderCallbacks);
        } else {
            showInternetUnavailableSnackbar();

        }
    }

    private void loadRecipesFromDB() {
        RecipesCursorLoader cursorLoaderCallbacks = new RecipesCursorLoader(this, this);
        getSupportLoaderManager().initLoader(
                Constants.RECIPES_CURSOR_LOADER_ID,
                null,
                cursorLoaderCallbacks);
    }

    private void setupRecyclerView() {
        mAdapter = new RecipesAdapter(null, this);
        recipesList.setAdapter(mAdapter);
        RecyclerView.LayoutManager layoutManager =
                new GridLayoutManager(this, 1, LinearLayoutManager.VERTICAL,false);
        recipesList.setLayoutManager(layoutManager);
    }
/*
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_refresh) {
            reloadRecipesFromServer();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

*/

    @Override
    public void onRecipesStartLoading() {
        showProgress();
    }

    @Override
    public void onRecipesLoaded(Recipe[] recipes) {
        hideProgress();
        if (recipes==null || recipes.length==0){
            showEmptyDisplay();
        } else {
            showRecyclerView();
            ContentValues[] valuesArray = DatabaseUtils.getRecipeContentValuesArray(recipes);
            getContentResolver().bulkInsert(BakifyProvider.Recipes.RECIPES_CONTENT_URI,valuesArray);
            valuesArray = DatabaseUtils.getIngredientsContentValuesArray(recipes);
            getContentResolver().bulkInsert(BakifyProvider.Ingredients.INGREDIENTS_CONTENT_URI, valuesArray);
            valuesArray = DatabaseUtils.getStepsContentValuesArray(recipes);
            getContentResolver().bulkInsert(BakifyProvider.Steps.STEPS_CONTENT_URI, valuesArray);
            PreferenceUtils.setRecipesInDatabase(this, true);
            setupRecipes();
        }
    }




    @Override
    public void onRecipeClicked(Recipe recipe) {
        //Start the new Activity with the Ingredients, Steps and Videos
        //Toast.makeText(this, recipe.getName(),Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, RecipeDetailActivity.class);
        Bundle bundle = new Bundle();
        bundle.putParcelable(Constants.INTENT_EXTRA_RECIPE, recipe);
        intent.putExtras(bundle);
        startActivity(intent);
    }



    @Override
    public void onRecipeCursorStartLoading() {
        showProgress();
    }

    @Override
    public void onRecipeCursorLoaded(Cursor cursor) {
        hideProgress();
        Recipe[] recipes = DatabaseUtils.getRecipeArrayFromCursor(cursor);
        mAdapter.swapRecipes(recipes);

    }

    private void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
    }

    private void hideProgress() {
        progressBar.setVisibility(View.INVISIBLE);
    }

    private void hideAllButProgressBar(){
        emptyDisplay.setVisibility(View.INVISIBLE);
        recipesList.setVisibility(View.INVISIBLE);
    }

    private void showEmptyDisplay() {
        emptyDisplay.setVisibility(View.VISIBLE);
        recipesList.setVisibility(View.INVISIBLE);
    }

    private void showRecyclerView() {
        emptyDisplay.setVisibility(View.INVISIBLE);
        recipesList.setVisibility(View.VISIBLE);
    }

    private void showInternetUnavailableSnackbar() {
        Snackbar.make(contentLayout, R.string.error_no_internet_connection, Snackbar.LENGTH_LONG)
                .setAction(R.string.btn_retry, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        loadRecipesFromServer();

                    }
                }).show();
    }
}
