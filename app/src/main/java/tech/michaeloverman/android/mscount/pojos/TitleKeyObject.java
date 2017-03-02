package tech.michaeloverman.android.mscount.pojos;

import android.support.annotation.NonNull;
import android.util.Log;


/**
 * Created by Michael on 2/25/2017.
 */

public class TitleKeyObject implements Comparable {
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

    @Override
    public int compareTo(@NonNull Object o) {
        TitleKeyObject other = (TitleKeyObject) o;
        return mTitle.compareTo(other.mTitle);
    }
}
