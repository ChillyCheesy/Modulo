package com.chillycheesy.hometracker.commands.operator.natif;

import com.chillycheesy.hometracker.commands.CommandFlux;
import com.chillycheesy.hometracker.commands.operator.NumberOperator;
import com.chillycheesy.hometracker.commands.operator.builder.Operator;
import com.chillycheesy.hometracker.commands.operator.builder.OperatorFindByRegex;
import com.chillycheesy.hometracker.modules.Module;
import com.chillycheesy.hometracker.utils.Priority;

/**
 * Operator that execute a division with the two parameter
 * This operator need number before and after the symbol "/"
 * Exemple :
 *          30 / 6 => 5
 *          -3 / 2 => -1.5
 */
@Operator(Priority.UNCOMMON)
@OperatorFindByRegex("/")
public class DivideOperator extends NumberOperator {

    @Override
    public CommandFlux onOperate(Module caller, CommandFlux left, CommandFlux center, CommandFlux right) {
        return super.applyOperation(left, center, right, "/", this::divide);
    }

    private double divide(double a, double b) {
        return a / b;
    }

}
