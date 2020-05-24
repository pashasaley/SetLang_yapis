package com.compiler.parser.sintaxtree;

import com.compiler.Context;

public class DoWhileStatement implements Statement {
    private final Expression condition;
    private final Statement statement;

    public DoWhileStatement(Expression condition, Statement statement) {
        this.condition = condition;
        this.statement = statement;
    }

    @Override
    public void execute() {
        Context.appendNewString("do {");
        statement.execute();
        Context.appendNewString(" }");
        Context.appendCurrentString(" while " + condition);
        Context.completeLine();
    }
}
