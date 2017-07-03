package ec.medinamobile.bakify.database;

import android.net.Uri;

import net.simonvt.schematic.annotation.ContentProvider;
import net.simonvt.schematic.annotation.ContentUri;
import net.simonvt.schematic.annotation.TableEndpoint;

/**
 * Created by Erick on 30/6/17.
 */

@ContentProvider(authority = BakifyProvider.AUTHORITY, database = BakifyDatabase.class)
public final class BakifyProvider {

    public static final String AUTHORITY = "ec.medinamobile.bakify.BakifyProvider";


  @TableEndpoint(table = BakifyDatabase.RECIPES) public static class Recipes {

    @ContentUri(
        path = "recipes",
        type = "vnd.android.cursor.dir/recipe",
        defaultSort = RecipesColumns.SERVER_ID + " ASC")
    public static final Uri RECIPES_CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/recipes");
  }

    @TableEndpoint(table = BakifyDatabase.INGREDIENTS) public static class Ingredients {

        @ContentUri(
                path = "ingredients",
                type = "vnd.android.cursor.dir/ingredient",
                defaultSort = IngredientsColumns._ID+ " ASC")
        public static final Uri INGREDIENTS_CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/ingredients");
    }

    @TableEndpoint(table = BakifyDatabase.STEPS) public static class Steps {

        @ContentUri(
                path = "steps",
                type = "vnd.android.cursor.dir/step",
                defaultSort = StepsColumns.SERVER_ID + " ASC")
        public static final Uri STEPS_CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/steps");
    }


}
