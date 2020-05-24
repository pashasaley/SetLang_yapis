package com.compiler.parser.sintaxtree;

import com.compiler.Context;
import com.compiler.vars.Variables;

public class ForStatement implements Statement {
    private Statement initStatement;
    private Expression termination;
    private Statement increment;
    private Statement block;

    public ForStatement(Statement initStatement, Expression termination, Statement increment, Statement block) {
        this.initStatement = initStatement;
        this.termination = termination;
        this.increment = increment;
        this.block = block;
    }

    @Override
    public void execute() {
        Variables.set(((AssignmentStatement) initStatement).getVariable(), ((AssignmentStatement) initStatement).getExpression().evaluate());
        Context.appendNewString("for (double " + initStatement.toString());
        Context.appendCurrentString("; ");
        Context.appendCurrentString(termination.toString());
        Context.appendCurrentString("; ");
        Context.appendCurrentString(increment.toString());
        Context.appendCurrentString(") {");
        block.execute();
        Context.appendNewString(" }");
    }
}
