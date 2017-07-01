package ec.medinamobile.bakify.database;

import net.simonvt.schematic.annotation.AutoIncrement;
import net.simonvt.schematic.annotation.DataType;
import net.simonvt.schematic.annotation.NotNull;
import net.simonvt.schematic.annotation.PrimaryKey;

import static net.simonvt.schematic.annotation.DataType.Type.INTEGER;
import static net.simonvt.schematic.annotation.DataType.Type.TEXT;

/**
 * Created by Erick on 30/6/17.
 */

public interface RecipesColumns {

    @DataType(INTEGER) @PrimaryKey @AutoIncrement String _ID = "_id";
    @DataType(INTEGER) @NotNull String SERVER_ID = "server_id";
    @DataType(TEXT) @NotNull String NAME = "name";
    @DataType(INTEGER) String SERVINGS = "servings";
    @DataType(TEXT) String IMAGE = "image";


}
