package com.chillycheesy.modulo.commands.operator.natif;

import com.chillycheesy.modulo.ModuloAPI;
import com.chillycheesy.modulo.commands.CommandContainer;
import com.chillycheesy.modulo.commands.CommandFlow;
import com.chillycheesy.modulo.commands.CommandManager;
import com.chillycheesy.modulo.commands.CommandProcessor;
import com.chillycheesy.modulo.commands.builder.CommandFlowBuilder;
import com.chillycheesy.modulo.commands.operator.BetweenOperator;
import com.chillycheesy.modulo.commands.operator.Operation;
import com.chillycheesy.modulo.commands.operator.builder.Operator;
import com.chillycheesy.modulo.commands.operator.OperatorListener;
import com.chillycheesy.modulo.modules.Module;
import com.chillycheesy.modulo.utils.Priority;
import com.chillycheesy.modulo.utils.exception.CommandException;

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
