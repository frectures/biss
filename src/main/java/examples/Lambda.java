package examples;

import java.util.function.IntUnaryOperator;

public class Lambda {
    public static IntUnaryOperator add(int a) {
        return b -> a + b;
    }
}
