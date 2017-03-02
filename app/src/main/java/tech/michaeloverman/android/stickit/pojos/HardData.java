package tech.michaeloverman.android.stickit.pojos;

/**
 * Created by Michael on 2/21/2017.
 */

public class HardData {
    public static int[] two4nextDisp = {1,3,5,7,9,11,13,15,17,19,21,23,25,27,29,31,33,35,37,39,41,43,45,47,49,51,53,55,57,59,61,63,65,67,69,71,73,75,77,79,81,83,85,87,89,91,93,95,97,99,101,103,105,107,109,111,113,115,117,119,121,123,125,127,129,131,133,135,137,139,141,143,145,147,149,151,153,155,157,159,161,163,165,167,169,171,173,175,177,179,181,183,185,187,189,191,193,195,197,199,201,203,205,207,209,211,213,215,217,219,221,223,225,227,229,231,233,235,237,239,241,243,245,247,249,251,253,255,2,4,6,8,10,12,14,16,18,20,22,24,26,28,30,32,34,36,38,40,42,44,46,48,50,52,54,56,58,60,62,64,66,68,70,72,74,76,78,80,82,84,86,88,90,92,94,96,98,100,102,104,106,108,110,112,114,116,118,120,122,124,126,128,130,132,134,136,138,140,142,144,146,148,150,152,154,156,158,160,162,164,166,168,170,172,174,176,178,180,182,184,186,188,190,192,194,196,198,200,202,204,206,208,210,212,214,216,218,220,222,224,226,228,230,232,234,236,238,240,242,244,246,248,250,252,254,256};
    public static int[] two4prevDisp = {1,129,2,130,3,131,4,132,5,133,6,134,7,135,8,136,9,137,10,138,11,139,12,140,13,141,14,142,15,143,16,144,17,145,18,146,19,147,20,148,21,149,22,150,23,151,24,152,25,153,26,154,27,155,28,156,29,157,30,158,31,159,32,160,33,161,34,162,35,163,36,164,37,165,38,166,39,167,40,168,41,169,42,170,43,171,44,172,45,173,46,174,47,175,48,176,49,177,50,178,51,179,52,180,53,181,54,182,55,183,56,184,57,185,58,186,59,187,60,188,61,189,62,190,63,191,64,192,65,193,66,194,67,195,68,196,69,197,70,198,71,199,72,200,73,201,74,202,75,203,76,204,77,205,78,206,79,207,80,208,81,209,82,210,83,211,84,212,85,213,86,214,87,215,88,216,89,217,90,218,91,219,92,220,93,221,94,222,95,223,96,224,97,225,98,226,99,227,100,228,101,229,102,230,103,231,104,232,105,233,106,234,107,235,108,236,109,237,110,238,111,239,112,240,113,241,114,242,115,243,116,244,117,245,118,246,119,247,120,248,121,249,122,250,123,251,124,252,125,253,126,254,127,255,128,256};
    public static int[] stonePattern = {85,170,51,204,75,105,45,90,17,238,119,137,15,83,172,84,86,82,81,174,87,168,80,52,54,50,60,49,206,55,200,48,73,182,77,178,68,187,78,72,79,176,109,146,102,153,110,104,111,144,34,221,46,40,47,208,30,23,232,16,120,112,28,227,36,219,109,146,79,52,203,12};
    public static Integer[] stoneUtility = {15,16,17,22,23,24,25,31,38,39,46,47,52,53,59,61};
    public static Integer[] strangeSecondUtility = {62,113,63,142,64,149,65,106,66,181,67,74,68,11,69,240,70,15,71,149};

    public static int[] testPatternBeats = {
            2,2,3,2,2,3,2,2,2,2,2,2,3,
            2,2,3,2,2,3,2,2,2,2,2,2,3
    };
    public static int[] testPatternDownBeats = {
            3,3,7,3,3,7,1
    };

//    public static int[] cirone12Beats = {
//            2,2,2,2,2,2,2,2,2,3,
//            2,2,2,2,2,2,2,2,2,3,
//            2,2,3,2,2,2,2,2,2,2,2,3,
//            2,3,2,2,3,2,2,3,
//            2,2,2,3,3,2,2,2,2,
//            2,2,3,3,3,3,3,2,2,2,2,
//            2,2,2,2,2,2,2,2,2,2,3,2,2,
//            3,2,2,3,2,2,3,2,2,
//            3,2,2,3,3,2,2,2,2,
//            2,2,2,2,2,2,2,3,3,1};
//    public static int[] cirone12DownBeats = {
//            2,2,2,1,2,1,
//            2,2,2,1,2,1,
//            2,1,2,2,2,2,1,
//            1,1,2,1,2,1,
//            2,1,1,1,2,2,
//            2,1,1,1,1,1,2,2,
//            2,2,2,2,2,1,2,
//            1,2,1,2,1,2,
//            1,2,1,1,2,2,
//            2,2,1,2,1,1,1 };

//    public static int[] cirone17Beats = {
//            3,3,3,2,3,3,3,2,3,
//            3,2,2,2,3,3,2,2,2,3,
//            3,3,2,2,3,2,2,2,2,3,2,
//            2,2,3,2,3,2,2,3,3,3,3,2,
//            3,3,3,2,3,2,3,2,3,2,
//            3,2,3,2,3,2,3,2,3,2,3,2,
//            2,3,3,2,3,2,2,
//            3,3,3,2,3,3,2,2,3,
//            3,3,3,2,3,3,3,
//            2,3,2,3,3,2,
//            3,3,3,3,3,3,2};
//    public static int[] cirone17DownBeats = {
//            1,1,1,1,1,1,1,1,1,
//            1,1,1,1,1,1,1,1,1,1,
//            1,2,2,1,1,1,1,1,1,
//            1,1,2,2,2,1,1,1,1,
//            1,1,1,1,2,2,2,
//            2,2,2,2,2,2,
//            2,2,2,1,
//            1,1,1,1,1,1,1,2,
//            1,1,1,1,1,1,1,
//            1,2,1,1,1,
//            1,1,1,1,1,1,1,1
//    };

//    public static int[] cirone16Beats = {
//            4,4,4,4,4,4,
//            4,4,4,4,4,4,
//            6,6,6,6,
//            6,6,6,6,
//            4,4,4,4,4,4,
//            4,4,4,4,4,4,
//            4,6,4,6,4,
//            4,6,4,6,4,
//            6,6,6,6,
//            6,6,6,6,
//            6,4,6,4,4,
//            6,4,6,4,4,
//            2,4,6,4,4,2,2,
//            2,4,6,4,4,2,2,
//            5,5,5,5,4,
//            5,5,5,5,4,
//            6,6,6,6,
//            6,6,6,6,
//            2,2,2,2,2,2,2,2,2,2,2,2,
//            2,2,2,2,2,2,2,2,2,2,2,2,
//            4,4,4,4,4,4,
//            4,4,4,4,4,4,
//            4,4,4,4,4,4,4,4,4
//    };
//    public static int[] cirone16DownBeats = {
//            3,3,3,3,1,1,1,1,1,1,1,1,2,2,2,2,2,2,
//            1,1,1,1,1,1,1,1,1,1,
//            3,1,3,1,2,2,1,2,2,1,
//            1,1,1,2,1,1,
//            1,1,1,2,1,1,
//            1,1,1,1,1,1,1,1,1,1,
//            2,2,2,2,
//            1,1,1,1,1,1,1,1,1,1,1,1,
//            1,1,1,1,1,1,1,1,1,1,1,1,
//            4,2,4,2,3,3,3
//    };

//    public static int[] bensonIBeats = {
//            3,2,3,2,3,2,3,2,3,2,3,2,3,2,3,2,2,3,3,2,
//            3,2,3,2,3,2,3,2,3,2,3,2,3,2,3,2,3,2,3,2,
//            2,3,2,3,2,3,2,3,3,2,3,2,3,2,3,2,3,2,3,2,
//            3,2,3,2,3,2,3,2,3,2,3,2,2,3,3,2,3,2,3,2,
//            2,3,3,2,2,2,2,
//            3,2,3,2,3,2,3,2,3,2,3,2,3,
//            3,2,3,3,2,3,3,2,3,3,2,2,3,3,2,3,2,3,2,3,2,3,2,3,2,3,2,3,2,3,
//            3,2,3,2,3,
//            3,2,3,2,3,2,3,2,3,2,3,2,3,2,3,2,3,2,3,2,
//            3,2,3,2,3,2,3,2,3,2,3,2,3,2,3,2,3,2,3,2
//    };
//    public static int[] bensonIDownBeats = {
//            2,2,2,2,2,2,2,2,2,2,
//            2,2,2,2,2,2,2,2,2,2,
//            2,2,2,2,2,2,2,2,2,2,
//            2,2,2,2,2,2,2,2,2,2,
//            2,2,3,
//            2,2,2,2,2,2,1,2,1,2,1,2,1,
//            2,2,2,2,2,2,2,2,2,2,1,
//            2,2,1,
//            2,2,2,2,2,2,2,2,2,2,
//            2,2,2,2,2,2,2,2,2,2
//    };

    // SUBDIVISION 2
    public static int[] cirone39Beats = {
            4,4,4,4,4,4,4,4,4,4,4,
            4,4,4,4,4,4,4,4,4,
            4,4,3,4,4,4,4,3,
            4,4,4,4,4,4,4,4,
            4,4,4,4,4,4,4,4,
            6,3,4,4,4,4,4,4,
            4,4,4,4,4,4,4,4,4,4,4,4,
            4,4,4,4,4,4,4,4,4,
            4,4,2,3,4,4,
            4,4,4,4,4,4,4,4,4,4,
            4,4,3,4,4,4,4
    };
    public static int[] cirone39DownBeats = {
            2,2,3,2,2,2,2,3,2,
            2,1,2,2,1,2,2,2,2,
            2,2,2,2,1,1,2,2,2,
            2,2,2,2,2,2,3,2,2,2,
            2,1,1,2,2,2,2,2,2,
            2,1,2,2
    };



    public static int[] cirone26Beats = {
            6,6,6,6,6,6,6,6,6,6,6,6,6,6,6,6,
            4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,6,6,6,6,
            6,6,6,6,6,6,6,6,6,6,6,6,3,2,5,5,5,
            5,5,5,5,6,6,6,6,6,6,6,6,6,6,6,6,
            6,6,6,6,6,6,6,6,6,6,6,6,6,
            6,6,6,6,6,6,6,6,6,6,6,6,6,6,6,6,
            6,6,6,6,6,6,6,6
    };
    public static int[] cirone26DownBeats = {
            4,4,4,4,2,2,2,2,2,2,2,2,4,
            4,4,4,2,1,1,1,1,1,1,1,4,4,4,
            4,4,4,1,4,4,4,4,4,4
    };

    public static int[] cirone34Beats = {
            3,2,3,2,3,2,3,2,3,2,3,2,3,2,3,2,3,2,
            3,2,3,2,3,2,3,2,3,2,3,2,3,2,3,2,3,2,
            3,2,3,2,3,2,3,2,3,2,3,2,3,2,3,2,
            3,2,3,2,3,2,3,2,3,2,3,2,3,2,3,2,
            3,2,3,2,3,2,3,2,3,2,3,2,3,2,3,2,3,2,3,2
    };
    public static int[] cirone34DownBeats = {
            2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,3,
            2,2,2,2,2,2,2,2,2,2,2,2,3,
            2,2,2,2,2,2,2,2,2,2
    };

    public static int[] cirone40Beats = {
            6,4,4,6,6,4,4,6,6,4,4,6,6,4,4,6,6,4,6,4,
            6,4,6,4,6,4,6,4,6,4,6,4,6,4,3,6,4,4,6,3,4,6,6,4,
            3,4,6,6,4,6,4,6,4,6,4,6,4,6,4,6,4,4,6,4,6,6,4,4,6,5,4,6,
            6,4,4,6,4,6,4,6,6,4,4,6,4,4,
            6,4,4,6,4,6,6,4
    };
    public static int[] cirone40DownBeats = {
            2,2,2,2,2,2,2,2,2,2,2,
            2,2,2,2,2,2,1,2,2,1,2,2,
            1,2,2,2,2,2,2,2,2,2,
            2,2,2,1,2,2,2,2,2,
            2,1,2,1,2,2,2,2
    };

    public static int[] cirone45Beats = {
            3,3,3,3,3,3,3,3,3,
            3,3,3,3,3,3,3,3,3,
            3,3,3,3,3,3,3,3,
            3,3,3,2,3,
            1,3,3,3,3,3,3,
            3,3,2,3,2,1,3,
            3,3,2,3,2,4,
            2,3,2,3,2,
            3,3,3,3,3,
            3,3,3,3,3,
            3,2,3,2,3,1
    };
    public static int[] cirone45DownBeats = {
            1,1,1,1,1,1,1,1,1,
            1,1,1,1,1,1,1,1,1,
            1,1,1,1,1,1,1,1,
            1,1,1,1,1,
            1,1,1,1,1,1,1,
            1,1,1,1,1,1,1,
            1,1,1,1,1,1,
            1,1,1,1,1,
            1,1,1,1,1,
            1,1,1,1,1,
            1,1,1,1,1,1
    };

    public static int[] cirone47Beats = {
            2,2,2,3,2,2,2,2,2,2,2,2,2,2,2,2,2,
            2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,
            3,2,3,2,2,2,2,2,2,2,2,2,3,
            2,2,2,2,2,2,2,2,3,2,2,2,2,2,3,
            2,2,2,2,2,2,2,2,2,2,2,3,3,2,2,
            1,3,2,2,2,2,2,2,2,2,2,2,2,2,2,
            2,2,2,2,2,2,2,2,3,2,2,2,2,2,
            2,2,2,2,2,2,2,2,2,2,2,2,2,3,2,
            2,2,2,2,2,3,2,2,2,2,2,2,2,2,2,2,2,
            2,2,3,2,1,2,2,2,2,2,3,2,2,
            3,2,3,2,2,3,2,3,2,1
    };
    public static int[] cirone47DownBeats = {
            3,1,4,1,8,
            2,3,6,6,
            1,2,2,1,5,1,1,
            8,1,4,1,1,
            2,3,4,3,2,1,
            1,1,3,5,2,3,
            2,6,1,1,4,
            2,8,3,1,1,
            4,1,1,8,3,2,1,1,1,3,2,1,2,2,3,1,1,1,1,1
    };

    public static int[] cirone49Beats = {
            2,2,3,2,3,3,2,3,2,3,
            2,3,2,3,2,3,2,2,3,3,3,3,
            3,2,2,2,2,3,2,2,3,2,2,3,
            2,3,2,2,3,2,2,3,2,2,3,3,
            2,3,2,3,2,3,2,3,2,2,
            2,3,2,3,3,2,3,2,3,2,
            3,2,2,2,2,3,2,2,3,2,2,3,
            3,2,2,2,2,3,3,2,3,2,
            2,3,3,3,3,2,3,
            2,3,3,2,3,2,3,2,2,3
    };
    public static int[] cirone49DownBeats = {
            3,2,2,1,1,1,
            2,2,2,3,1,1,1,
            2,1,2,1,3,3,
            2,3,3,3,1,
            1,2,2,1,2,2,
            2,2,2,1,1,1,1,
            3,3,3,3,
            3,3,1,1,2,
            2,1,1,1,1,1,
            1,1,1,2,2,3
    };


    public static String[] composers = {
//            "Cirone, Anthony",
//            "Cirone, Anthony",
//            "Cirone, Anthony",
//            "Cirone, Anthony",
//            "Cirone, Anthony",
//            "Cirone, Anthony",
//            "Cirone, Anthony"
    };
    public static String[] titles = {
//            "Portraits in Rhythm 39",
//            "Portraits in Rhythm 26",
//            "Portraits in Rhythm 34",
//            "Portraits in Rhythm 40",
//            "Portraits in Rhythm 45",
//            "Portraits in Rhythm 47",
//            "Portraits in Rhythm 49",
    };
    public static int[][] lotsObeats = {
//            cirone39Beats,
//            cirone26Beats,
//            cirone34Beats,
//            cirone40Beats,
//            cirone45Beats,
//            cirone47Beats,
//            cirone49Beats
    };
    public static int[][] lotsOdownBeats = {
//            cirone39DownBeats,
//            cirone26DownBeats,
//            cirone34DownBeats,
//            cirone40DownBeats,
//            cirone45DownBeats,
//            cirone47DownBeats,
//            cirone49DownBeats
    };
    public static int[] subdivisions = {
//            4,6,3,6,3,6,2
//            3
    };
    public static int[] countoffsubdivisions = {
//            2,3,2,3,3,3,2
//            3
    };
}
