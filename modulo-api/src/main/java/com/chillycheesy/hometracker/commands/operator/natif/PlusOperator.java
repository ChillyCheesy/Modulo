package com.chillycheesy.hometracker.commands.operator.natif;

import com.chillycheesy.hometracker.commands.*;
import com.chillycheesy.hometracker.commands.operator.NumberOperator;
import com.chillycheesy.hometracker.commands.operator.builder.Operator;
import com.chillycheesy.hometracker.commands.operator.builder.OperatorFindByRegex;
import com.chillycheesy.hometracker.modules.Module;
import com.chillycheesy.hometracker.utils.Priority;

@Operator(Priority.NEUTRAL)
@OperatorFindByRegex("\\+")
public class PlusOperator extends NumberOperator {

    @Override
    public CommandFlux onOperate(Module caller, CommandFlux left, CommandFlux center, CommandFlux right) {
        return super.applyOperation(left, center, right, "\\+", this::plus);
    }

    private double plus(double a, double b) {
        return a + b;
    }

}
