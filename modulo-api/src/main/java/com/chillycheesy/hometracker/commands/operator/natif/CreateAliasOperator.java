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
@OperatorFindByRegex("=")
public class CreateAliasOperator implements OperatorListener {

    @Override
    public CommandFlux onOperate(Module module, CommandFlux left, CommandFlux center, CommandFlux right) throws CommandException {
        final String leftContent = stringify(left);
        final String rightContent = stringify(right);
        return FluxBuilder.create("alias " + leftContent + " " + rightContent, center.getAliasManager());
    }

    private String stringify(CommandFlux flux) {
        return flux.getContent().replaceAll("^[^\"].*[^\"]$", "\"$0\"");
    }

}
