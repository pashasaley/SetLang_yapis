package com.compiler.parser.sintaxtree;


import com.compiler.vars.NumberValue;
import com.compiler.vars.Value;
import com.compiler.vars.Variables;

public class VariableExpression implements Expression {
    private final String name;

    public VariableExpression(String name) {
        this.name = name;
    }

    @Override
    public Value evaluate() {
        if(!Variables.isExists(name)){
            Variables.set(name, new NumberValue(0));
        }
        return Variables.get(name);
    }

    @Override
    public String toString() {
        return String.format("%s", name);
    }
}
