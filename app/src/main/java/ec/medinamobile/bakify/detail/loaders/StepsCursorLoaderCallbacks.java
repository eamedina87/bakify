package ec.medinamobile.bakify.detail.loaders;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;

import ec.medinamobile.bakify.database.BakifyProvider;
import ec.medinamobile.bakify.database.StepsColumns;
import ec.medinamobile.bakify.entities.Step;
import ec.medinamobile.bakify.utils.DatabaseUtils;

/**
 * Created by Supertel on 3/7/17.
 */

public class StepsCursorLoaderCallbacks implements LoaderManager.LoaderCallbacks<Cursor> {

    private Context mContext;
    private OnStepCursorLoaderListener mListener;

    public StepsCursorLoaderCallbacks(Context context, OnStepCursorLoaderListener listener){
        mContext = context;
        mListener = listener;
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        Uri mUri = BakifyProvider.Steps.STEPS_CONTENT_URI;
        int recipeId = -1;
        if (args!=null){
            recipeId = args.getInt(StepsColumns.RECIPE_ID);
        }
        return new CursorLoader(mContext,
                mUri,
                null,
                DatabaseUtils.getStepsSelection(),
                DatabaseUtils.getStepsSelectionArgs(recipeId),
                null);

    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        Step[] steps = null;
        if (data!=null) steps = DatabaseUtils.getStepArrayFromCursor(data);
        mListener.onStepsLoaded(steps);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mContext = null;
        mListener = null;
    }
}
