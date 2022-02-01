package com.chillycheesy.hometracker.commands.operator.natif;

import com.chillycheesy.hometracker.ModuloAPI;
import com.chillycheesy.hometracker.commands.*;
import com.chillycheesy.hometracker.commands.operator.Operation;
import com.chillycheesy.hometracker.commands.operator.builder.Operator;
import com.chillycheesy.hometracker.commands.operator.OperatorListener;
import com.chillycheesy.hometracker.modules.Module;
import com.chillycheesy.hometracker.utils.Priority;
import com.chillycheesy.hometracker.utils.exception.CommandException;

@Operator(Priority.HIGH)
public class CommandStatusOperator extends BetweenOperator implements OperatorListener {

    @Override
    public CommandFlux onOperate(Module module, CommandFlux left, CommandFlux center, CommandFlux right) throws CommandException {
        final String content = center.getContent().replaceAll("^\\[|]$", "");
        center.setContent(content);
        final CommandContainer commandContainer = ModuloAPI.getCommand();
        final CommandManager manager = commandContainer.getCommandManager();
        final CommandProcessor processor = manager.getProcessor();
        final CommandFlux flux = processor.execute(module, center);
        return FluxBuilder.combine(left.getAliasManager(), left, FluxBuilder.create(flux.isSuccess() + "", left.getAliasManager()), right);
    }

    @Override
    public Operation findOperatorMatch(CommandFlux flux) {
        return super.findOperatorMatch(flux, '[', ']');
    }

}
