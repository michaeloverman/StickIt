package tech.michaeloverman.android.stickit.pojos;

import android.util.Log;

/**
 * Created by Michael on 2/20/2017.
 */

public class PieceOfMusic {
    private static final int COUNTOFF_LENGTH = 4;
    private static final String TAG = PieceOfMusic.class.getSimpleName();
    private String mTitle;
    private String mAuthor;
    private int[] mBeats;
    private int[] mDownBeats;
    private int mSubdivision;
    private int mCountOffMeasureLength;

    public PieceOfMusic(String title) {
        Log.d(TAG, "PieceOfMusic constructor()");
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
    }

    public int[] getBeats() {
        return mBeats;
    }

    /**
     * Accepts array of the length of each beat, by number of primary subdivisions,
     * combines that with a generated count off measure, and saves the entire array.
     *
     * @param beats
     */
    public void setBeats(int[] beats) {
//        insureLCD(beats);
        int[] countoff = buildCountoff(beats[0]);
        mBeats = combine(countoff, beats);
        printArray(mBeats);
    }

    private void printArray(int[] array) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < array.length; i++) {
            sb.append(array[i] + ", ");
        }
        Log.d(TAG, sb.toString());
    }

    /** Uses the 'length' of first beat to generate count off measure */
    private int[] buildCountoff(int firstBeat) {
        mCountOffMeasureLength = COUNTOFF_LENGTH + firstBeat - 1;
        int[] countOff = new int[mCountOffMeasureLength];
        int i;
        for (i = 0; i < countOff.length; ) {
            if (i != COUNTOFF_LENGTH - 2) {
                countOff[i++] = firstBeat;
            } else {
                for (int j = 0; j < firstBeat; j++) {
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

    public int[] getDownBeats() {
        return mDownBeats;
    }

    public void setDownBeats(int[] downBeats) {
        int[] allDownBeats = new int[downBeats.length + 1];
        allDownBeats[0] = mCountOffMeasureLength;
        System.arraycopy(downBeats, 0, allDownBeats, 1, downBeats.length);
        mDownBeats = allDownBeats;
    }
}
