package common;

public class Exceptions {
    @SuppressWarnings("unchecked")
    public static <X extends Exception, R> R sneakyThrow(Exception x) throws X {
        throw (X) x;
    }
}
