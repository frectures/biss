package examples;

public class Assertion {
    public static String[] splitAt(String s, int index) {
        assert index >= 0 && index <= s.length() : "index " + index + " out of range " + s.length();

        String prefix = s.substring(0, index);
        String suffix = s.substring(index);
        return new String[]{prefix, suffix};
    }
}
