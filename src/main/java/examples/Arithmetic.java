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

    public static double[] solveQuadraticEquation(double p, double q) {
        double minusHalfP = -0.5 * p;
        double radicand = minusHalfP * minusHalfP - q;
        if (radicand < 0) {
            return EMPTY;
        } else if (radicand == 0) {
            return new double[] { minusHalfP };
        } else {
            double root = Math.sqrt(radicand);
            return new double[] { minusHalfP - root, minusHalfP + root };
        }
    }

    private static final double[] EMPTY = new double[0];
}
