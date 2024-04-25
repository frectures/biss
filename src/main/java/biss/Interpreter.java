package biss;

import java.util.Map;
import java.util.function.DoubleBinaryOperator;
import java.util.function.DoubleUnaryOperator;
import java.util.regex.Pattern;

public class Interpreter {
    public static void main(String[] args) {
        DoubleUnaryOperator f = interpreted("root(-x * x + 2 * 5 * 2 * 5) / 2 / 2 - 1 - 1");
        double y = f.applyAsDouble(6);
        System.out.println(y);
    }

    private static final Pattern LEXEMES = Pattern.compile("x|[0-9]+|[-+*/]|root|[^\\s]");

    private static final Map<String, DoubleBinaryOperator> OPERATIONS = Map.of(
            "*", (a, b) -> a * b,
            "/", (a, b) -> a / b,
            "+", (a, b) -> a + b,
            "-", (a, b) -> a - b);

    public static DoubleUnaryOperator interpreted(String formula) {
        return x -> new Parser<Double>(LEXEMES.matcher(formula)) {
            @Override
            public Double get() {
                double result = subexpression(0);
                expect(END);
                return result;
            }

            private double subexpression(int leftPrecedence) {
                double result = primary();
                int rightPrecedence;
                // a + B * c    + < *
                // a * B + c    * > +
                while (leftPrecedence < (rightPrecedence = PRECEDENCE.getOrDefault(lexeme, -1))) {
                    String operator = accept();
                    double subresult = subexpression(rightPrecedence);
                    result = OPERATIONS.get(operator).applyAsDouble(result, subresult);
                }
                return result;
            }

            private double primary() {
                switch (lexeme.charAt(0)) {
                    case '0':
                    case '1':
                    case '2':
                    case '3':
                    case '4':
                    case '5':
                    case '6':
                    case '7':
                    case '8':
                    case '9':
                        return Double.parseDouble(accept());

                    case 'x':
                        advance();
                        return x;

                    case '-':
                        advance();
                        return -subexpression(14);

                    case '(':
                        advance();
                        double parenthesized = subexpression(0);
                        expect(")");
                        return parenthesized;

                    case 'r':
                        advance();
                        expect("(");
                        double radicand = subexpression(0);
                        expect(")");
                        return Math.sqrt(radicand);

                    default:
                        throw new IllegalArgumentException(lexeme);
                }
            }
        }.get();
    }
}
