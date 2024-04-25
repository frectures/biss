package examples;

import java.util.function.Supplier;

public class Bridge implements Supplier<String> {
    @Override
    public String get() {
        return "Hello World";
    }
}
