package examples;

import java.util.concurrent.locks.ReentrantLock;

public class Synchronization {
    public static synchronized int synchronizedMethod() {
        return 42;
    }

    public static int synchronizedBlock() {
        synchronized (Synchronization.class) {
            return 42;
        }
    }

    public static int locked() {
        reentrantLock.lock();
        try {
            System.out.println("And the answer is...");
            return 42;
        } finally {
            reentrantLock.unlock();
        }
    }

    private static final ReentrantLock reentrantLock = new ReentrantLock();
}
