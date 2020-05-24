package com.compiler;

import com.compiler.parser.LexicalAnalyzer;
import com.compiler.parser.Parser;
import com.compiler.parser.Token;
import com.compiler.parser.sintaxtree.Statement;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class Compiler {
    public static void main(String[] args) throws IOException {
        final String inputText1 = new String(Files.readAllBytes(Paths.get(args[0])), "UTF-8");
        final List<Token> tokens = new LexicalAnalyzer(inputText1).tokenize();
        final Statement statement = new Parser(tokens).parse();
        statement.execute();

        ResultFileBuilder.generate(Context.getContext(), Context.getMethods());
    }
}
