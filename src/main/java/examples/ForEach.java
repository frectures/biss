package examples;

import java.util.List;

public class ForEach {
    public static void overArray(String[] strings) {
        for (String s : strings) {
            System.out.println(s);
        }
    }

    public static void overList(List<String> strings) {
        for (String s : strings) {
            System.out.println(s);
        }
    }
}
