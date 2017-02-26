package tech.michaeloverman.android.stickit.utils;

import android.util.Log;

import java.util.List;

/**
 * Created by Michael on 2/26/2017.
 */

public class MetronomeUtilities {
    public static final String TAG = MetronomeUtilities.class.getSimpleName();

    public static int[] integerListToArray(List<Integer> integerList) {
        int[] ints = new int[integerList.size()];
        for(int i = 0; i < integerList.size(); i++) {
            ints[i] = integerList.get(i);
        }
        return ints;
    }

    public static void printArray(int[] array) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < array.length; i++) {
            sb.append(array[i] + ", ");
        }
        Log.d(TAG, sb.toString());
    }
}
