package com.compiler.parser.sintaxtree;

import com.compiler.Context;

public final class BreakStatement extends RuntimeException implements Statement {

    @Override
    public void execute() {
        Context.appendNewString("break");
        Context.completeLine();
    }
}
