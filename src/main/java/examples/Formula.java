package examples;

import java.util.function.DoubleUnaryOperator;

public class Formula implements DoubleUnaryOperator {
    @Override
    public double applyAsDouble(double x) {
        return Math.sqrt(-x * x + 2 * 5 * 2 * 5) / 2 / 2 - 1 - 1;
    }
}
