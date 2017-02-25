package tech.michaeloverman.android.stickit;

import android.content.Context;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import tech.michaeloverman.android.stickit.pojos.HardData;

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

    private int[] two4nextDisp = HardData.two4nextDisp;
    private int[] two4prevDisp = HardData.two4prevDisp;
    private static final int[] stonePattern = HardData.stonePattern;
    private static final Map<Integer, Integer> stonePatternMap;
    private static final Integer[] stoneUtility = HardData.stoneUtility;
    private static final Set<Integer> stoneDoublePatterns = new HashSet<>(Arrays.asList(stoneUtility));
    private static final Integer[] strangeSecondUtility = HardData.strangeSecondUtility;
    private static final Map<Integer, Integer> strangeSecondHalf;
    static {
        Map<Integer, Integer> stoneMap = new HashMap<>();
        for (int i = 1; i < 73; i++) {
            stoneMap.put(i, stonePattern[i-1]);
        }
        stonePatternMap = Collections.unmodifiableMap(stoneMap);
        Map<Integer, Integer> strangeMap = new HashMap<>();
        strangeMap.put(63,113);
        strangeMap.put(64,142);
        strangeMap.put(65,149);
        strangeMap.put(66,106);
        strangeMap.put(67,181);
        strangeMap.put(68,74);
        strangeMap.put(69,11);
        strangeMap.put(70,240);
        strangeMap.put(71,15);
        strangeMap.put(72,149);
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
        currentStone = (currentStone + 1) % 72;
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
            currentStone = 1;
            currentSequence = stonePatternMap.get(currentStone);
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
        checkIfStone();
    }

    private void checkIfStone() {
        if(doingStones) {
            if(stonePatternMap.containsValue(currentSequence)) {
                for(int i : stonePatternMap.keySet()) {
                    if(stonePatternMap.get(i) == currentSequence) {
                        currentStone = i;
                        break;
                    }
                }
            }
        }
    }

    public static int cycleDown(int pattern) {
        return (pattern - 1) & bitMask;
    }
    public void cycleDown() {
        currentSequence = cycleDown(currentSequence);
        checkIfStone();
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
        checkIfStone();
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
        checkIfStone();
    }
    public static int switchHands(int pattern) {
        return ~ pattern;
    }
}
