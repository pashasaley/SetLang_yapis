package com.compiler.parser.sintaxtree;

import com.compiler.vars.Value;

public interface Expression {
    Value evaluate();
}
