package ec.medinamobile.bakify.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Erick on 1/7/17.
 */

public class PreferenceUtils {

    private final static String PREFERENCES_NAME = "bakify_prefs";
    private final static String PREF_RECIPES_IN_DB = "pref_recipes_in_db";

    public static boolean areRecipesInDatabase(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE);
        return preferences.getBoolean(PREF_RECIPES_IN_DB,false);
    }

    public static void setRecipesInDatabase(Context context, boolean value){
        SharedPreferences preferences = context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE);
        preferences.edit().putBoolean(PREF_RECIPES_IN_DB, value).apply();
    }


}
