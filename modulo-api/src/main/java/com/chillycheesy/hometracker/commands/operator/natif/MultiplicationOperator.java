package com.chillycheesy.hometracker.commands.operator.natif;

import com.chillycheesy.hometracker.commands.CommandFlux;
import com.chillycheesy.hometracker.commands.operator.builder.Operator;
import com.chillycheesy.hometracker.commands.operator.builder.OperatorFindByRegex;
import com.chillycheesy.hometracker.modules.Module;
import com.chillycheesy.hometracker.utils.Priority;

@Operator(Priority.MAJOR)
@OperatorFindByRegex("\\*")
public class MultiplicationOperator extends NumberOperator {

    @Override
    public CommandFlux onOperate(Module caller, CommandFlux left, CommandFlux center, CommandFlux right) {
        return super.applyOperation(left, center, right, "\\*", this::multiple);
    }

    private double multiple(double a, double b) {
        return a * b;
    }

}