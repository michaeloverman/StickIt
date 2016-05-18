package tech.michaeloverman.android.stickit;

import android.content.res.AssetManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class MainActivity extends AppCompatActivity {

    private int[] nextDisp;
    private static final String FILE_FOLDER = "sequences";
    private AssetManager mAssets;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAssets = this.getAssets();
        int[] numLines;
        String[] fileNames;
        try {
            fileNames = mAssets.list(FILE_FOLDER);
//            int[] numLines;
            int i = 0;
            Log.i("FILE", "gitten files, found: " + fileNames.length);
            for (String fileName : fileNames) {
                try {
                    String path = FILE_FOLDER + "/" + fileName;
                    numLines[i++] = countLines(path);
                } catch (IOException ioe) {
                    numLines[i++] = 0;
                    System.out.println("IOException caught in countLines()");
                    ioe.printStackTrace();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("Number of lines: " + numLines[0]);
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
