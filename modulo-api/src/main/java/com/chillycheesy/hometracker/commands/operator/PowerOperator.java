package com.chillycheesy.hometracker.commands.operator;

import com.chillycheesy.hometracker.commands.CommandFlux;
import com.chillycheesy.hometracker.commands.NumberOperator;
import com.chillycheesy.hometracker.modules.Module;
import com.chillycheesy.hometracker.utils.Priority;

@Operator(Priority.MAJOR)
@OperatorFindByRegex("\\^")
public class PowerOperator extends NumberOperator implements OperatorListener {

    @Override
    public CommandFlux onOperate(Module caller, CommandFlux left, CommandFlux center, CommandFlux right) {
        return super.applyOperation(left, center, right, "\\^", this::pow);
    }

    private double pow(double a, double b) {
        return Math.pow(a, b);
    }
}