package ec.medinamobile.bakify.database;

import net.simonvt.schematic.annotation.Database;
import net.simonvt.schematic.annotation.Table;

/**
 * Created by Erick on 30/6/17.
 */
@Database(version = BakifyDatabase.VERSION)
public final class BakifyDatabase {

    public static final int VERSION = 1;
    @Table(RecipesColumns.class) public static final String RECIPES = "recipes";
    @Table(IngredientsColumns.class) public static final String INGREDIENTS = "ingredients";
    @Table(StepsColumns.class) public static final String STEPS = "steps";

}
