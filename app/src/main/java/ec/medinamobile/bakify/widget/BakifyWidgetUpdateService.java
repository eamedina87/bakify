package ec.medinamobile.bakify.widget;

import android.app.Service;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.IntDef;
import android.support.annotation.Nullable;

import ec.medinamobile.bakify.utils.Constants;

/**
 * Created by Erick on 9/7/17.
 */

public class BakifyWidgetUpdateService extends Service {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {

        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent!=null && intent.hasExtra(Constants.INTENT_EXTRA_RECIPE_ID)){
            int recipeId = intent.getIntExtra(Constants.INTENT_EXTRA_RECIPE_ID, -1);
            AppWidgetManager manager = AppWidgetManager.getInstance(this);
            int[] ids = manager.getAppWidgetIds(new ComponentName(this, BakifyWidgetProvider.class));
            BakifyWidgetProvider.updateAppWidget(this, manager, ids, recipeId);
        }
        return super.onStartCommand(intent, flags, startId);
    }
}
