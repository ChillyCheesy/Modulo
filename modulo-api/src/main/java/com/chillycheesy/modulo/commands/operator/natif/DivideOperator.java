package com.chillycheesy.modulo.commands.operator.natif;

import com.chillycheesy.modulo.commands.CommandFlow;
import com.chillycheesy.modulo.commands.operator.NumberOperator;
import com.chillycheesy.modulo.commands.operator.builder.Operator;
import com.chillycheesy.modulo.commands.operator.builder.OperatorFindByRegex;
import com.chillycheesy.modulo.modules.Module;
import com.chillycheesy.modulo.utils.Priority;

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
    public CommandFlow onOperate(Module caller, CommandFlow left, CommandFlow center, CommandFlow right) {
        return super.applyOperation(left, center, right, "/", this::divide);
    }

    private double divide(double a, double b) {
        return a / b;
    }

}
