package biss;

import java.util.Map;
import java.util.function.Supplier;
import java.util.regex.Matcher;

public abstract class Parser<R> implements Supplier<R> {
    protected static final String END = "<END>";

    protected static final Map<String, Integer> PRECEDENCE = Map.of(
            "*", 12, "/", 12, "+", 11, "-", 11);

    private Matcher matcher;
    protected String lexeme;

    protected Parser(Matcher matcher) {
        this.matcher = matcher;
        advance();
    }

    protected void advance() {
        if (matcher.find()) {
            lexeme = matcher.group();
        } else {
            lexeme = END;
        }
    }

    protected String accept() {
        String result = lexeme;
        advance();
        return result;
    }

    protected void expect(String expected) {
        if (!lexeme.equals(expected)) {
            throw new IllegalArgumentException("expected " + expected + " but found " + lexeme);
        }
        advance();
    }
}
