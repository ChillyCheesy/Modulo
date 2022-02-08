package com.chillycheesy.hometracker.commands.operator.natif;

import com.chillycheesy.hometracker.commands.CommandFlow;
import com.chillycheesy.hometracker.commands.operator.CompareOperator;
import com.chillycheesy.hometracker.commands.operator.builder.Operator;
import com.chillycheesy.hometracker.commands.operator.builder.OperatorFindByRegex;
import com.chillycheesy.hometracker.modules.Module;
import com.chillycheesy.hometracker.utils.Priority;
import com.chillycheesy.hometracker.utils.exception.CommandException;

@Operator(Priority.COMMON)
@OperatorFindByRegex(">=")
public class GreaterOrEqualsThanOperator extends CompareOperator {

    @Override
    public CommandFlow onOperate(Module module, CommandFlow left, CommandFlow center, CommandFlow right) throws CommandException {
        return super.applyOperation(left, center, right, ">=", this::greaterOrEqualsThan);
    }

    private boolean greaterOrEqualsThan(double a, double b){
        return a >= b;
    }
}
