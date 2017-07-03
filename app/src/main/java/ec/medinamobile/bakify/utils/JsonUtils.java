package ec.medinamobile.bakify.utils;

import android.content.Context;

import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import ec.medinamobile.bakify.R;
import ec.medinamobile.bakify.entities.Recipe;

/**
 * Created by Erick on 30/6/17.
 */

public class JsonUtils {

    //(TODO) Check why isnt server_id being assigned

    public static Recipe[] getRecipesFromJson(String recipesJson){
        if (recipesJson==null) return null;
        Gson gson = new Gson();
        Recipe[] recipes = gson.fromJson(recipesJson, Recipe[].class);
        return recipes;
    }

    public static String readJsonFromAssets(Context context) {
        String json = null;
        try {
            InputStream is = context.getResources().openRawResource(R.raw.recipes);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return json;
    }


}
