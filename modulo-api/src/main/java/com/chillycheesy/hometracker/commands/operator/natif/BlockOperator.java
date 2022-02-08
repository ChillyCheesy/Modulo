package com.chillycheesy.hometracker.commands.operator.natif;

import com.chillycheesy.hometracker.ModuloAPI;
import com.chillycheesy.hometracker.commands.*;
import com.chillycheesy.hometracker.commands.builder.CommandFlowBuilder;
import com.chillycheesy.hometracker.commands.operator.BetweenOperator;
import com.chillycheesy.hometracker.commands.operator.Operation;
import com.chillycheesy.hometracker.commands.operator.builder.Operator;
import com.chillycheesy.hometracker.commands.operator.OperatorListener;
import com.chillycheesy.hometracker.modules.Module;
import com.chillycheesy.hometracker.utils.Priority;
import com.chillycheesy.hometracker.utils.exception.CommandException;

@Operator(Priority.UNBREAKABLE)
public class BlockOperator extends BetweenOperator implements OperatorListener {

    @Override
    public CommandFlow onOperate(Module module, CommandFlow left, CommandFlow center, CommandFlow right) throws CommandException {
        final CommandFlow flux = new CommandFlow(center.getContent().replaceAll("^\\{|}$", ""), center.getAliasManager().createChild());
        final CommandContainer commandContainer = ModuloAPI.getCommand();
        final CommandManager manager = commandContainer.getCommandManager();
        final CommandProcessor processor = manager.getProcessor();
        final CommandFlow result = processor.execute(module, flux);
        return CommandFlowBuilder.combine(left.getAliasManager(), left, result, right);
    }

    @Override
    public Operation findOperatorMatch(CommandFlow flux) {
        return super.findOperatorMatch(flux, '{', '}');
    }

}
