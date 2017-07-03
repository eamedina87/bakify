package ec.medinamobile.bakify.main.loaders;

import android.database.Cursor;

/**
 * Created by Supertel on 1/7/17.
 */

public interface OnRecipeCursorLoadingListener {
    void onRecipeCursorStartLoading();
    void onRecipeCursorLoaded(Cursor cursor);
}
