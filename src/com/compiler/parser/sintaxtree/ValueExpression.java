package com.compiler.parser.sintaxtree;


import com.compiler.vars.NumberValue;
import com.compiler.vars.StringValue;
import com.compiler.vars.Value;

public class ValueExpression implements Expression {
    private final Value value;

    public ValueExpression(double value) {
        this.value = new NumberValue(value);
    }

    public ValueExpression(String value) {
        this.value = new StringValue(value);
    }

    @Override
    public Value evaluate() {
        return value;
    }

    @Override
    public String toString() {
        return value.asString();
    }
}
