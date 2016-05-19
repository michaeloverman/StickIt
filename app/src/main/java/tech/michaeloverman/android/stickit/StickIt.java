package tech.michaeloverman.android.stickit;

import android.content.Context;
import android.content.res.AssetManager;
import android.util.Log;

import java.io.IOException;

/**
 * Created by Michael on 5/18/2016.
 */
public class StickIt {
    private static final String TAG = "StickIt";

    private static final String FILES_FOLDER = "sequences";

    private AssetManager mAssets;

    public StickIt(Context context) {
        mAssets = context.getAssets();
        loadStickings();
    }

    private void loadStickings() {
        String[] fileNames;
        try {
            fileNames = mAssets.list(FILES_FOLDER);
            Log.i(TAG, "Found " + fileNames.length + " files");
        } catch (IOException ioe) {
            Log.e(TAG, "Can not list assets", ioe);
            return;
        }


    }
}
