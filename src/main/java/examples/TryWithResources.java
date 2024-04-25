package examples;

import java.io.*;

public class TryWithResources {
    public static void main(String[] args) throws IOException {
        try (PrintWriter printWriter = new PrintWriter(new FileWriter("output.txt"))) {
            printWriter.println("Hello World!");
        }
    }
}
