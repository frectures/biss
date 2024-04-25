package examples;

public class Arithmetic {
    public static int fingers(int hands) {
        return 5 * hands;
    }

    public static int days(int weeks) {
        return 7 * weeks;
    }

    public static float pythagoras(float x1, float y1, float x2, float y2) {
        return (float) Math.sqrt((x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2));
    }
}
