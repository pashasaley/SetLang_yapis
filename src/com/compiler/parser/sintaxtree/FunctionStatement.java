package com.compiler.parser.sintaxtree;

public class FunctionStatement implements Statement {
    private final FunctionalExpression function;

    public FunctionStatement(FunctionalExpression function) {
        this.function = function;
    }

    @Override
    public void execute() {
        function.evaluate();
    }
}
