package com.chillycheesy.hometracker.commands.operator.natif;

import com.chillycheesy.hometracker.commands.CommandFlux;
import com.chillycheesy.hometracker.commands.operator.BooleanOperator;
import com.chillycheesy.hometracker.commands.operator.builder.Operator;
import com.chillycheesy.hometracker.commands.operator.builder.OperatorFindByRegex;
import com.chillycheesy.hometracker.modules.Module;
import com.chillycheesy.hometracker.utils.Priority;
import com.chillycheesy.hometracker.utils.exception.CommandException;

@Operator(Priority.LOOK_AT_THIS_DUDE)
@OperatorFindByRegex("((\\&\\|)|(\\|\\&))")
public class XorOperator extends BooleanOperator {

    @Override
    public CommandFlux onOperate(Module module, CommandFlux left, CommandFlux center, CommandFlux right) throws CommandException {
        return super.applyOperation(left, center, right, "((\\&\\|)|(\\|\\&))", this::xor);
    }

    private boolean xor(boolean left, boolean right){
        return left ^ right;
    }
}
