package ec.medinamobile.bakify.widget;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import ec.medinamobile.bakify.R;
import ec.medinamobile.bakify.database.BakifyProvider;
import ec.medinamobile.bakify.entities.Recipe;
import ec.medinamobile.bakify.utils.Constants;
import ec.medinamobile.bakify.utils.DatabaseUtils;

/**
 * Created by Erick on 9/7/17.
 */

public class BakifyWidgetRecipesService extends RemoteViewsService {

    @Override
    public RemoteViewsRecipesFactory onGetViewFactory(Intent intent) {
        return new RemoteViewsRecipesFactory(this.getApplicationContext());
    }

    class RemoteViewsRecipesFactory implements RemoteViewsService.RemoteViewsFactory {

        private final Context mContext;
        private Recipe[] mRecipes;

        public RemoteViewsRecipesFactory(Context context) {
            mContext = context;
        }

        @Override
        public void onCreate() {

        }

        @Override
        public void onDataSetChanged() {
            Uri mUri = BakifyProvider.Recipes.RECIPES_CONTENT_URI;
            Cursor mCursor = mContext.getContentResolver().query(mUri, null, null, null, null);
            mRecipes = DatabaseUtils.getRecipeArrayFromCursor(mCursor);
            mCursor.close();
        }

        @Override
        public void onDestroy() {
            mRecipes = null;
        }

        @Override
        public int getCount() {
            return mRecipes!=null?mRecipes.length:0;
        }

        @Override
        public RemoteViews getViewAt(int i) {

            Recipe mRecipe = mRecipes[i];

            RemoteViews views = new RemoteViews(mContext.getPackageName(), R.layout.widget_item_recipe);
            views.setTextViewText(R.id.widget_recipe_name,mRecipe.getName());


            Bundle bundle = new Bundle();
            bundle.putInt(Constants.INTENT_EXTRA_RECIPE_ID, mRecipe.getId());
            Intent intent = new Intent();
            intent.putExtras(bundle);
            views.setOnClickFillInIntent(R.id.widget_recipe_name,intent);

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
            return mRecipes[i].getId();
        }

        @Override
        public boolean hasStableIds() {
            return false;
        }
    }

}
