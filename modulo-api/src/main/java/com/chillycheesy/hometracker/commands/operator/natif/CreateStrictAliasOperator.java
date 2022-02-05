package com.chillycheesy.hometracker.commands.operator.natif;

import com.chillycheesy.hometracker.commands.CommandFlux;
import com.chillycheesy.hometracker.commands.FluxBuilder;
import com.chillycheesy.hometracker.commands.operator.builder.Operator;
import com.chillycheesy.hometracker.commands.operator.builder.OperatorFindByRegex;
import com.chillycheesy.hometracker.commands.operator.OperatorListener;
import com.chillycheesy.hometracker.modules.Module;
import com.chillycheesy.hometracker.utils.Priority;
import com.chillycheesy.hometracker.utils.exception.CommandException;

@Operator(Priority.COMMON)
@OperatorFindByRegex("=>")
public class CreateStrictAliasOperator implements OperatorListener {

    @Override
    public CommandFlux onOperate(Module module, CommandFlux left, CommandFlux center, CommandFlux right) throws CommandException {
        final String leftContent = left.getContent();
        final String rightContent = right.getContent();
        return FluxBuilder.create("alias '" + leftContent + "' '" + rightContent + "'", center.getAliasManager());
    }

}
