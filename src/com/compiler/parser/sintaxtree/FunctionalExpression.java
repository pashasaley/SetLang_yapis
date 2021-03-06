package com.compiler.parser.sintaxtree;

import com.compiler.Context;
import com.compiler.vars.*;

import java.util.ArrayList;
import java.util.List;

public class FunctionalExpression implements Expression {
    private final String name;
    private final List<Expression> arguments;

    public FunctionalExpression(String name) {
        this.name = name;
        arguments = new ArrayList<>();
    }

    public FunctionalExpression(String name, List<Expression> arguments) {
        this.name = name;
        this.arguments = arguments;
    }

    public void addArgument(Expression arg) {
        arguments.add(arg);
    }

    @Override
    public Value evaluate() {
        /*
        * Write to file
        * */
        Context.appendNewString(name + "(");
        int counter = 0;
        for (Expression arg : arguments) {
            if(counter == 0) {
                Context.appendCurrentString(arg.toString());
            } else {
                Context.appendCurrentString(", " + arg.toString());
            }
            counter++;
        }
        Context.appendCurrentString(")");
        Context.completeLine();

        final int size = arguments.size();
        final Value[] values = new Value[size];
        for (int index = 0; index < size; index++) {       }
        final Function function = Functions.get(name);
        if (function instanceof UserDefinedFunction) {
            final UserDefinedFunction userDefinedFunction = (UserDefinedFunction) function;
            if(size != userDefinedFunction.getArgsCount()) {
                throw new RuntimeException("Args count mismatch");
            }
            Variables.push();   // push vars in stack for get copy of this
            for(int index = 0; index < size; index++) {
                Variables.set(userDefinedFunction.getArgsName(index), values[index]);
            }
            Variables.pop();
        }
        return null;
    }
}
