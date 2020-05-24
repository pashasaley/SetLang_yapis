package com.compiler.parser.sintaxtree;

import com.compiler.Context;

public final class ContinueStatement extends RuntimeException implements Statement {

    @Override
    public void execute() {
        Context.appendNewString("continue");
        Context.completeLine();
    }
}
