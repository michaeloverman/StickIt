package tech.michaeloverman.android.stickit;

import android.content.Context;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created by Michael on 5/18/2016.
 */
public class Sequence {
    private static final String TAG = "Sequence";
    private static int patternLength;
    private static boolean breakUpSpace = true;
    private static int breakUpDiff = 0;
    public static final int CLOSED_SPACING = 0;
    public static final int OPEN_SPACING_BIG_SMALL = 1;
    public static final int OPEN_SPACING_SMALL_BIG = 2;
    private boolean doingStones = false;

//    private static final String FILES_FOLDER = "sequences";

//    private AssetManager mAssets;

    private int[] two4nextDisp = {1,3,5,7,9,11,13,15,17,19,21,23,25,27,29,31,33,35,37,39,41,43,45,47,49,51,53,55,57,59,61,63,65,67,69,71,73,75,77,79,81,83,85,87,89,91,93,95,97,99,101,103,105,107,109,111,113,115,117,119,121,123,125,127,129,131,133,135,137,139,141,143,145,147,149,151,153,155,157,159,161,163,165,167,169,171,173,175,177,179,181,183,185,187,189,191,193,195,197,199,201,203,205,207,209,211,213,215,217,219,221,223,225,227,229,231,233,235,237,239,241,243,245,247,249,251,253,255,2,4,6,8,10,12,14,16,18,20,22,24,26,28,30,32,34,36,38,40,42,44,46,48,50,52,54,56,58,60,62,64,66,68,70,72,74,76,78,80,82,84,86,88,90,92,94,96,98,100,102,104,106,108,110,112,114,116,118,120,122,124,126,128,130,132,134,136,138,140,142,144,146,148,150,152,154,156,158,160,162,164,166,168,170,172,174,176,178,180,182,184,186,188,190,192,194,196,198,200,202,204,206,208,210,212,214,216,218,220,222,224,226,228,230,232,234,236,238,240,242,244,246,248,250,252,254,256};
    private int[] two4prevDisp = {1,129,2,130,3,131,4,132,5,133,6,134,7,135,8,136,9,137,10,138,11,139,12,140,13,141,14,142,15,143,16,144,17,145,18,146,19,147,20,148,21,149,22,150,23,151,24,152,25,153,26,154,27,155,28,156,29,157,30,158,31,159,32,160,33,161,34,162,35,163,36,164,37,165,38,166,39,167,40,168,41,169,42,170,43,171,44,172,45,173,46,174,47,175,48,176,49,177,50,178,51,179,52,180,53,181,54,182,55,183,56,184,57,185,58,186,59,187,60,188,61,189,62,190,63,191,64,192,65,193,66,194,67,195,68,196,69,197,70,198,71,199,72,200,73,201,74,202,75,203,76,204,77,205,78,206,79,207,80,208,81,209,82,210,83,211,84,212,85,213,86,214,87,215,88,216,89,217,90,218,91,219,92,220,93,221,94,222,95,223,96,224,97,225,98,226,99,227,100,228,101,229,102,230,103,231,104,232,105,233,106,234,107,235,108,236,109,237,110,238,111,239,112,240,113,241,114,242,115,243,116,244,117,245,118,246,119,247,120,248,121,249,122,250,123,251,124,252,125,253,126,254,127,255,128,256};
    private static final int[] stonePattern = {85,170,51,204,75,105,45,90,17,238,119,137,15,83,172,84,86,82,81,174,87,168,80,52,54,50,60,49,206,55,200,48,73,182,77,178,68,187,78,72,79,176,109,146,102,153,110,104,111,144,34,221,46,40,47,208,30,23,232,16,120,112,28,227,36,219,109,146,79,52,203,12};
    private static final Map<Integer, Integer> stonePatternMap;
    private static final Integer[] stoneUtility = {15,16,17,22,23,24,25,31,38,39,46,47,52,53,59,61};
    private static final Set<Integer> stoneDoublePatterns = new HashSet<>(Arrays.asList(stoneUtility));
    private static final Integer[] strangeSecondUtility = {62,113,63,142,64,149,65,106,66,181,67,74,68,11,69,240,70,15,71,149};
    private static final Map<Integer, Integer> strangeSecondHalf;
    static {
        Map<Integer, Integer> stoneMap = new HashMap<>();
        for (int i = 1; i < 73; i++) {
            stoneMap.put(i, stonePattern[i-1]);
        }
        stonePatternMap = Collections.unmodifiableMap(stoneMap);
        Map<Integer, Integer> strangeMap = new HashMap<>();
        strangeMap.put(62,113);
        strangeMap.put(63,142);
        strangeMap.put(64,149);
        strangeMap.put(65,106);
        strangeMap.put(66,181);
        strangeMap.put(67,74);
        strangeMap.put(68,11);
        strangeMap.put(69,240);
        strangeMap.put(70,15);
        strangeMap.put(71,149);
        strangeSecondHalf = Collections.unmodifiableMap(strangeMap);
    }
    private static int currentStone;
    private int[] wasteDigit;

    private int currentSequence;
    private static int bitMask;

    public Sequence(Context context, int length) {
        newSequenceLength(length);
    }
    public void newSequenceLength(int length) {
        patternLength = length;

        bitMask = (1 << patternLength) - 1;

        currentSequence = 0b0101010101010101;
        if (patternLength % 2 == 1) {
            currentSequence = currentSequence << 1;
            breakUpDiff = 0;
        }
        currentSequence = currentSequence & bitMask;

    }
    public void setSequence(int s) {
        currentSequence = s;
    }
    public void setNewSpacing(int option) {
        switch (option) {
            case CLOSED_SPACING:
                breakUpSpace = false;
                break;
            case OPEN_SPACING_BIG_SMALL:
                breakUpSpace = true;
                breakUpDiff = 0;
                break;
            case OPEN_SPACING_SMALL_BIG:
                breakUpDiff = -1;
                breakUpSpace = true;
                break;
            default:
        }
    }
    private void loadStickings() {

    }
    public boolean isDoingStones() {
        return doingStones;
    }
    public void nextStone() {
        currentStone = (currentStone + 1) % 73;
        currentSequence = stonePatternMap.get(currentStone);
    }
    public void previousStone() {
        currentStone--;
        if(currentStone < 1) currentStone = 72;
        currentSequence = stonePatternMap.get(currentStone);
    }
    public int getCurrentStone() {
        return currentStone;
    }
    public boolean isStone() {
        if(stonePatternMap.containsValue(currentSequence)) return true;
        return false;
    }
    public void setDoingStones(boolean bool) {
        doingStones = bool;
        if(doingStones) {
            currentStone = 0;
            currentSequence = stonePattern[currentStone];
        }
    }
    public static String patternToString(int p) {
        StringBuffer sb = new StringBuffer();
        for (int i =patternLength -1; i >= patternLength / 2 - breakUpDiff; i--) {
            if (((p >> i) & 1) == 0) sb.append("R");
            else sb.append("L");
        }
        if (breakUpSpace) sb.append(" ");
        for (int i = (patternLength / 2) - 1 - breakUpDiff; i >= 0; i--) {
            if (((p >> i) & 1) == 0) sb.append("R");
            else sb.append("L");
        }
        return sb.toString();
    }
    public String toString() {
        return toString(currentSequence);
    }
    public String toString(int pattern) {
        StringBuffer sb = new StringBuffer();
//        if (patternLength == 16) {
//            for (int i = 15; i >= 12; i--) {
//                if (((pattern >> i) & 1) == 0) sb.append("R");
//                else sb.append("L");
//            }
//            sb.append(" ");
//            for (int i = 11; i >= 8; i--) {
//                if (((pattern >> i) & 1) == 0) sb.append("R");
//                else sb.append("L");
//            }
//            sb.append("\n");
//            for (int i = 7; i >= 4; i--) {
//                if (((pattern >> i) & 1) == 0) sb.append("R");
//                else sb.append("L");
//            }
//            sb.append(" ");
//            for (int i = 3; i >= 0; i--) {
//                if (((pattern >> i) & 1) == 0) sb.append("R");
//                else sb.append("L");
//            }
//            return sb.toString();
//        }
        for (int i =patternLength -1; i >= patternLength / 2 - breakUpDiff; i--) {
            if (((pattern >> i) & 1) == 0) sb.append("R");
            else sb.append("L");
        }
        if (breakUpSpace) sb.append(" ");
        for (int i = (patternLength / 2) - 1 - breakUpDiff; i >= 0; i--) {
            if (((pattern >> i) & 1) == 0) sb.append("R");
            else sb.append("L");
        }
        if(doingStones) {
            if (stoneDoublePatterns.contains(currentStone)) {
                sb.append("\n");
                sb.append(patternToString(pattern ^ ((1 << patternLength) - 1)));
            } else if (currentStone > 61) {
                sb.append("\n");
                sb.append(patternToString(strangeSecondHalf.get(currentStone)));
            }
        }
        return sb.toString();
    }
    public int getInt() {
        return currentSequence;
    }
    public static int cycleUp(int pattern) {
        return (pattern + 1) & bitMask;
    }
    public void cycleUp() {
        currentSequence = cycleUp(currentSequence);
    }
    public static int cycleDown(int pattern) {
        return (pattern - 1) & bitMask;
    }
    public void cycleDown() {
        currentSequence = cycleDown(currentSequence);
    }
    public static int rhythmicDisplaceR(int pattern) {
        int bit = (pattern & 1) << (patternLength - 1);
        pattern = pattern >> 1;
//        System.out.println("XORing " + Integer.toBinaryString(pattern) + " with " +
//                Integer.toBinaryString(bit));
        return (pattern ^ bit) & bitMask;
    }
    public void rhythmicDisplaceR() {
        currentSequence = rhythmicDisplaceR(currentSequence);
    }
    public static int rhythmicDisplaceL(int pattern) {
        int bit = (pattern >> (patternLength -1)) & 1;
        pattern = pattern << 1;
//        System.out.println("XORing " + Integer.toBinaryString(pattern) + " with " +
//                Integer.toBinaryString(bit));
        return (pattern ^ bit) & bitMask;
    }
    public void rhythmicDisplaceL() {
        currentSequence = rhythmicDisplaceL(currentSequence);
    }
    public static int switchHands(int pattern) {
        return ~ pattern;
    }
}
