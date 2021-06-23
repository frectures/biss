package examples;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Switch {
    public static String overInts(int x) {
        switch (x) {
            case 2:
            case 3:
            case 5:
            case 7:
                return "prime digit";

            case 4:
            case 8:
            case 15:
            case 16:
            case 23:
            case 42:
                return "bad luck";

            default:
                return null;
        }
    }

    public static List<Integer> overStrings(String s) {
        switch (s) {
            case "prime digit":
                return Arrays.asList(2, 3, 5, 7);

            case "bad luck":
                return Arrays.asList(4, 8, 15, 16, 23, 42);

            default:
                return Collections.emptyList();
        }
    }
}
