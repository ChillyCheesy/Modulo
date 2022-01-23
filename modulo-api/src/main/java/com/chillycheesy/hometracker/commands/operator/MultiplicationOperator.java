package com.chillycheesy.hometracker.commands.operator;

import com.chillycheesy.hometracker.commands.CommandFlux;
import com.chillycheesy.hometracker.commands.NumberOperator;
import com.chillycheesy.hometracker.utils.Priority;

@Operator(Priority.MAJOR)
@OperatorFindByRegex("\\*")
public class MultiplicationOperator extends NumberOperator {

    @Override
    public CommandFlux onOperate(CommandFlux left, CommandFlux center, CommandFlux right) {
        return super.applyOperation(left, center, right, "\\*", this::multiple);
    }

    private double multiple(double a, double b) {
        return a * b;
    }

}
