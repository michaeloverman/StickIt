package tech.michaeloverman.android.stickit;

import android.content.res.AssetManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;

public class StickItActivity extends tech.michaeloverman.android.stickit.SingleFragmentActivity {
    private static final String TAG = "StickItActivity";

    private int[] nextDisp;
    private static final String FILE_FOLDER = "sequences";
    private AssetManager mAssets;

    @Override
    protected Fragment createFragment() {
        System.out.println("StickItActivity createFragment()");
        return MetronomeSelectorFragment.newInstance();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        System.out.println("StickItActivity onCreate()");

    }



}
