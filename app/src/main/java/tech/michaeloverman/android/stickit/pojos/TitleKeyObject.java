package tech.michaeloverman.android.stickit.pojos;

import android.util.Log;


/**
 * Created by Michael on 2/25/2017.
 */

public class TitleKeyObject {
    private static final String TAG = TitleKeyObject.class.getSimpleName();

    String mTitle;
    String mKey;

    public TitleKeyObject(String title, String key) {
        Log.d(TAG, "TitleKeyObject constructor(): " + title + ", " + key);
        mTitle = title;
        mKey = key;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public String getKey() {
        return mKey;
    }

    public void setKey(String key) {
        mKey = key;
    }
}
