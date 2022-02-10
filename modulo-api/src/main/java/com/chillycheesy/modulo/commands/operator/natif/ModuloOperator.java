package com.chillycheesy.modulo.commands.operator.natif;

import com.chillycheesy.modulo.commands.CommandFlow;
import com.chillycheesy.modulo.commands.operator.NumberOperator;
import com.chillycheesy.modulo.commands.operator.OperatorListener;
import com.chillycheesy.modulo.commands.operator.builder.Operator;
import com.chillycheesy.modulo.commands.operator.builder.OperatorFindByRegex;
import com.chillycheesy.modulo.modules.Module;
import com.chillycheesy.modulo.utils.Priority;

/**
 * Operator that return the values of the left parameter modulo the right one.
 * This operator need number before and after the symbol "%".
 * Exemple:
 * <ul>
 *     <li>10 % 2 = 0</li>
 *     <li>13 % 5 = 3</li>
 * </ul>
 */
@Operator(Priority.UNCOMMON)
@OperatorFindByRegex("%")
public class ModuloOperator extends NumberOperator implements OperatorListener {

    @Override
    public CommandFlow onOperate(Module caller, CommandFlow left, CommandFlow center, CommandFlow right) {
        return super.applyOperation(left, center, right, "%", this::modulo);
    }

    private double modulo(double a, double b) {
        return a % b;
    }
}
