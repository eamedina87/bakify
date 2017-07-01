package ec.medinamobile.bakify.utils;

import com.google.gson.Gson;

import java.util.ArrayList;

import ec.medinamobile.bakify.entities.Recipe;

/**
 * Created by Supertel on 30/6/17.
 */

public class JsonUtils {

    public static Recipe[] getRecipesFromJson(String recipesJson){

        Gson gson = new Gson();
        Recipe[] recipes = gson.fromJson(recipesJson, Recipe[].class);
        return recipes;

    }


}
