package tech.michaeloverman.android.stickit.pojos;

import android.util.Log;

import java.util.List;

/**
 * Created by Michael on 2/20/2017.
 */

public class PieceOfMusic {
    private static final String TAG = PieceOfMusic.class.getSimpleName();
    private static final int COUNTOFF_LENGTH = 4;
    private String mTitle;
    private String mAuthor;
    private List<Integer> mBeats;
    private List<Integer> mDownBeats;
    private int mSubdivision;
    private int mCountOffSubdivision;
    private int mCountOffMeasureLength;

    public PieceOfMusic(String title) {
        Log.d(TAG, "PieceOfMusic constructor()");
        mTitle = title;
    }

    public PieceOfMusic() { }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public String getAuthor() {
        return mAuthor;
    }

    public void setAuthor(String author) {
        mAuthor = author;
    }

    public int getSubdivision() {
        return mSubdivision;
    }

    public void setSubdivision(int subdivision) {
        mSubdivision = subdivision;
        mCountOffSubdivision = mSubdivision;
    }

    public int getCountOffSubdivision() {
        return mCountOffSubdivision;
    }

    public void setCountOffSubdivision(int countOffSubdivision) {
        mCountOffSubdivision = countOffSubdivision;
    }

    public static int[] beatsListToArray(List<Integer> beatsList) {
        int[] beats = new int[beatsList.size()];
        for(int i = 0; i < beatsList.size(); i++) {
            beats[i] = beatsList.get(i);
        }
        return beats;
    }

    public List<Integer> getBeats() {
        return mBeats;
    }

    /**
     * Accepts array of the length of each beat, by number of primary subdivisions,
     * combines that with a generated count off measure, and saves the entire array.
     *
     * @param beats
     */
    public void setBeats(List<Integer> beats) {
//        int[] countoff = buildCountoff(mSubdivision);
//        int[] allBeats = combine(countoff, beats);
//        mBeats = new ArrayList<>();
//        for(int i = 0; i < allBeats.length; i++) {
//            mBeats.add(allBeats[i]);
//        }
//        printArray(mBeats);
        mBeats = beats;
    }

//    public void setBeatsWithDoubles(double[] beats) {
//        int[] bigbeats = new int[beats.length];
//        for(int i = 0; i < beats.length; i++) {
//            bigbeats[i] = (int) (beats[i] * 100);
//        }
//        int gcd = findGCD(bigbeats);
//        for(int i = 0; i < bigbeats.length; i++) {
//            bigbeats[i] /= gcd;
//        }
//        mCountOffSubdivision = mSubdivision;
//        mSubdivision = mSubdivision * 100 / gcd;
//        setBeats(bigbeats);
//    }

//    private int findGCD(int[] input) {
//        int result = input[0];
//        for(int i = 0; i < input.length; i++) result = findGCD(result, input[i]);
//        return result;
//    }
//    private int findGCD(int a, int b) {
//        while (b > 0) {
//            int temp = b;
//            b = a % b;
//            a = temp;
//        }
//        return a;
//    }

    private void printArray(int[] array) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < array.length; i++) {
            sb.append(array[i] + ", ");
        }
        Log.d(TAG, sb.toString());
    }

    /** Uses the 'length' of first beat to generate count off measure */
    private int[] buildCountoff(int subdivision) {
        mCountOffMeasureLength = COUNTOFF_LENGTH + subdivision - 1;
        int[] countOff = new int[mCountOffMeasureLength];
        int i;
        for (i = 0; i < countOff.length; ) {
            if (i != COUNTOFF_LENGTH - 2) {
                countOff[i++] = subdivision;
            } else {
                for (int j = 0; j < subdivision; j++) {
                    countOff[i++] = 1;
                }
            }
        }
        return countOff;
    }

    private int[] combine(int[] countoff, int[] beats) {
        Log.d(TAG, "combine() arrays...");
        int[] combination = new int[countoff.length + beats.length];
        System.arraycopy(countoff, 0, combination, 0, countoff.length);
        System.arraycopy(beats, 0, combination, countoff.length, beats.length);
        return combination;
    }


    public List<Integer> getDownBeats() {
        return mDownBeats;
    }

    public void setDownBeats(List<Integer> downBeats) {
//        int[] allDownBeats = new int[downBeats.length + 1];
//        allDownBeats[0] = mCountOffMeasureLength;
//        System.arraycopy(downBeats, 0, allDownBeats, 1, downBeats.length);
//
//        mDownBeats = new ArrayList<>();
//        for (int i = 0; i < allDownBeats.length; i++) {
//            mDownBeats.add(allDownBeats[i]);
//        }
        mDownBeats = downBeats;
    }
}
