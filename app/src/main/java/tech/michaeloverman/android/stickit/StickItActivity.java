package tech.michaeloverman.android.stickit;

import android.content.res.AssetManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class StickItActivity extends tech.michaeloverman.android.stickit.SingleFragmentActivity {
    private static final String TAG = "StickItActivity";

    private int[] nextDisp;
    private static final String FILE_FOLDER = "sequences";
    private AssetManager mAssets;

    @Override
    protected Fragment createFragment() {
        System.out.println("StickItActivity createFragment()");
        return StickItFragment.newInstance();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        System.out.println("StickItActivity onCreate()");

    }


    public static int countLines(String filename) throws IOException {
        InputStream is = new BufferedInputStream(new FileInputStream(filename));
        try {
            byte[] c = new byte[1024];
            int count = 0;
            int readChars = 0;
            boolean empty = true;
            while ((readChars = is.read(c)) != -1) {
                empty = false;
                for (int i = 0; i < readChars; ++i) {
                    if (c[i] == '\n') {
                        ++count;
                    }
                }
            }
            return (count == 0 && !empty) ? 1 : count;
        } finally {
            is.close();
        }
    }
}
