package com.chillycheesy.hometracker.commands.operator.natif;

import com.chillycheesy.hometracker.commands.CommandFlow;
import com.chillycheesy.hometracker.commands.operator.NumberOperator;
import com.chillycheesy.hometracker.commands.operator.builder.Operator;
import com.chillycheesy.hometracker.commands.operator.builder.OperatorFindByRegex;
import com.chillycheesy.hometracker.commands.operator.OperatorListener;
import com.chillycheesy.hometracker.modules.Module;
import com.chillycheesy.hometracker.utils.Priority;

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
