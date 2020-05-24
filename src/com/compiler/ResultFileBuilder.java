package com.compiler;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class ResultFileBuilder {
    public static void generate(String context, String methods) throws IOException {
        String mainClass = "public class Main {";
        mainClass += "\n\tpublic static void main(String[] args) {";

        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("Main.java"));

        bufferedWriter.write(mainClass);

        bufferedWriter.append(context);
        bufferedWriter.append("\n\t}");

        bufferedWriter.append("\n");
        bufferedWriter.append(methods);

        bufferedWriter.append("\n}");

        bufferedWriter.close();
    }
}
