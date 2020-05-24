package com.compiler.parser.sintaxtree;

import com.compiler.Context;

public class WhileStatement implements Statement {
    private final Expression condition;
    private final Statement statement;

    public WhileStatement(Expression condition, Statement statement) {
        this.condition = condition;
        this.statement = statement;
    }

    @Override
    public void execute() {
        Context.appendNewString("while " + condition + " {");
        statement.execute();
        Context.appendNewString(" }");

    }
}
