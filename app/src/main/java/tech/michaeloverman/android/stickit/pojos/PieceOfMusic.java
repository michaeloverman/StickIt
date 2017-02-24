package tech.michaeloverman.android.stickit.pojos;

import android.util.Log;

import java.util.ArrayList;
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
    }

    public int[] beatsArray() {
        int[] beats = new int[mBeats.size()];
        for(int i = 0; i < mBeats.size(); i++) {
            beats[i] = mBeats.get(i);
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
    public void setBeats(int[] beats) {
//        insureLCD(beats);
        int[] countoff = buildCountoff(mSubdivision);
        int[] allBeats = combine(countoff, beats);
        mBeats = new ArrayList<>();
        for(int i = 0; i < allBeats.length; i++) {
            mBeats.add(allBeats[i]);
        }
//        printArray(mBeats);
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

    public int[] downBeatsArray() {
        int[] beats = new int[mDownBeats.size()];
        for(int i = 0; i < mDownBeats.size(); i++) {
            beats[i] = mDownBeats.get(i);
        }
        return beats;
    }

    public List<Integer> getDownBeats() {
        return mDownBeats;
    }

    public void setDownBeats(int[] downBeats) {
        int[] allDownBeats = new int[downBeats.length + 1];
        allDownBeats[0] = mCountOffMeasureLength;
        System.arraycopy(downBeats, 0, allDownBeats, 1, downBeats.length);

        mDownBeats = new ArrayList<>();
        for (int i = 0; i < allDownBeats.length; i++) {
            mDownBeats.add(allDownBeats[i]);
        }
    }
}
