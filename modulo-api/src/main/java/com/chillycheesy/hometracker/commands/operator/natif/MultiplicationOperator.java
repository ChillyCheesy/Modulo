package com.chillycheesy.hometracker.commands.operator.natif;

import com.chillycheesy.hometracker.commands.CommandFlow;
import com.chillycheesy.hometracker.commands.operator.NumberOperator;
import com.chillycheesy.hometracker.commands.operator.builder.Operator;
import com.chillycheesy.hometracker.commands.operator.builder.OperatorFindByRegex;
import com.chillycheesy.hometracker.modules.Module;
import com.chillycheesy.hometracker.utils.Priority;

/**
 * Operator that execute a multiplication with the two parameter
 * This operator need number before and after the symbol "*"
 * Exemple :
 *          5 * 6 => 30
 *          -3 * 2 => -6
 */
@Operator(Priority.UNCOMMON)
@OperatorFindByRegex("\\*")
public class MultiplicationOperator extends NumberOperator {

    @Override
    public CommandFlow onOperate(Module caller, CommandFlow left, CommandFlow center, CommandFlow right) {
        return super.applyOperation(left, center, right, "\\*", this::multiple);
    }

    private double multiple(double a, double b) {
        return a * b;
    }

}
