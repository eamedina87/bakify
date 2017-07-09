package ec.medinamobile.bakify.widget;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import ec.medinamobile.bakify.R;
import ec.medinamobile.bakify.database.BakifyProvider;
import ec.medinamobile.bakify.entities.Ingredient;
import ec.medinamobile.bakify.utils.AdapterUtils;
import ec.medinamobile.bakify.utils.Constants;
import ec.medinamobile.bakify.utils.DatabaseUtils;

/**
 * Created by Erick on 9/7/17.
 */

public class BakifyWidgetIngredientsService extends RemoteViewsService {
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new RemoteViewsIngredientsFactory(getApplicationContext(), intent);
    }

    class RemoteViewsIngredientsFactory implements RemoteViewsFactory{

        private final Context mContext;
        private int mRecipeId;
        private Ingredient[] mIngredients;

        public RemoteViewsIngredientsFactory(Context applicationContext, Intent intent) {
            mContext = applicationContext;
            if (intent!=null && intent.hasExtra(Constants.INTENT_EXTRA_RECIPE_ID)){
                mRecipeId = intent.getIntExtra(Constants.INTENT_EXTRA_RECIPE_ID, -1);
            }

        }



        @Override
        public void onCreate() {

        }

        @Override
        public void onDataSetChanged() {
            Cursor mCursor = mContext.getContentResolver().query(
                    BakifyProvider.Ingredients.INGREDIENTS_CONTENT_URI,
                    null,
                    DatabaseUtils.getIngredientsSelection(),
                    DatabaseUtils.getIngredientsSelectionArgs(mRecipeId),
                    null);
            mIngredients = DatabaseUtils.getIngredientArrayFromCursor(mCursor);
            mCursor.close();
        }

        @Override
        public void onDestroy() {
            mIngredients = null;
        }

        @Override
        public int getCount() {
            return mIngredients==null?0:mIngredients.length;
        }

        @Override
        public RemoteViews getViewAt(int i) {
            Ingredient mIngredient = mIngredients[i];
            RemoteViews views = new RemoteViews(mContext.getPackageName(), R.layout.widget_item_ingredient);
            views.setTextViewText(R.id.ingredient_name, mIngredient.getIngredient());
            views.setTextViewText(R.id.ingredient_quantity, AdapterUtils.getStringFromFloat(mIngredient.getQuantity()));
            views.setImageViewResource(R.id.ingredient_measure, AdapterUtils.getImageResourceForMeasure(mIngredient.getMeasure()));
            return views;
        }

        @Override
        public RemoteViews getLoadingView() {
            return null;
        }

        @Override
        public int getViewTypeCount() {
            return 1;
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public boolean hasStableIds() {
            return false;
        }
    }

}
