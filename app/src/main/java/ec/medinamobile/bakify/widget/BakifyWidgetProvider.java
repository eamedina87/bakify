package ec.medinamobile.bakify.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import ec.medinamobile.bakify.R;
import ec.medinamobile.bakify.utils.Constants;

/**
 * Implementation of App Widget functionality.
 *
 */
public class BakifyWidgetProvider extends AppWidgetProvider {

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId, int recipeId) {

        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.bakify_widget);
        Intent intentRecipes = new Intent(context, BakifyWidgetRecipesService.class);
        views.setRemoteAdapter(R.id.widget_recipes, intentRecipes);

        Intent intentUpdate = new Intent(context, BakifyWidgetUpdateService.class);
        PendingIntent pendingIntent = PendingIntent.getService(context, Constants.PENDING_INTENT_UPDATE_WIDGET_ID, intentUpdate, PendingIntent.FLAG_UPDATE_CURRENT);
        views.setPendingIntentTemplate(R.id.widget_recipes, pendingIntent);

        Intent intentIngredients = new Intent(context, BakifyWidgetIngredientsService.class);
        intentIngredients.putExtra(Constants.INTENT_EXTRA_RECIPE_ID, recipeId);
        views.setRemoteAdapter(R.id.widget_ingredients, intentIngredients);

        appWidgetManager.updateAppWidget(appWidgetId, views);
        appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetId, R.id.widget_ingredients);

    }

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int[] appWidgetIds, int recipeId) {

        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId,recipeId);
        }

    }


    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId,1);
        }
    }

    @Override
    public void onDeleted(Context context, int[] appWidgetIds) {
        // When the user deletes the widget, delete the preference associated with it.

    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}

