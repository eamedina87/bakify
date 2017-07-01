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

public interface StepsColumns {

    @DataType(INTEGER) @PrimaryKey @AutoIncrement String _ID = "_id";
    @DataType(INTEGER) @NotNull String SERVER_ID = "server_id";
    @DataType(TEXT) @NotNull String  SHORT_DESCRIPTION = "short_description";
    @DataType(TEXT) @NotNull String DESCRIPTION = "description";
    @DataType(TEXT) String VIDEO_URL = "videoURL";
    @DataType(TEXT) String THUBMNAIL_URL = "thubmnailURL";

}
