package ec.medinamobile.bakify.database;

import net.simonvt.schematic.annotation.AutoIncrement;
import net.simonvt.schematic.annotation.DataType;
import net.simonvt.schematic.annotation.NotNull;
import net.simonvt.schematic.annotation.PrimaryKey;

import java.util.ArrayList;


import static net.simonvt.schematic.annotation.DataType.Type.INTEGER;
import static net.simonvt.schematic.annotation.DataType.Type.TEXT;

/**
 * Created by Erick on 30/6/17.
 */

public interface IngredientsColumns {

    @DataType(INTEGER) @PrimaryKey @AutoIncrement String _ID = "_id";
    @DataType(INTEGER) @NotNull String  RECIPE_ID = "recipe_id";
    @DataType(INTEGER) @NotNull String QUANTITY = "quantity";
    @DataType(TEXT) @NotNull String MEASURE = "measure";
    @DataType(TEXT) @NotNull String INGREDIENT = "ingredient";
}
