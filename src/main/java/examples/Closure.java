package examples;

import java.util.function.IntUnaryOperator;

public class Closure {
    public static IntUnaryOperator add(int a) {
        return new IntUnaryOperator() {
            @Override
            public int applyAsInt(int b) {
                return a + b;
            }
        };
    }
}
