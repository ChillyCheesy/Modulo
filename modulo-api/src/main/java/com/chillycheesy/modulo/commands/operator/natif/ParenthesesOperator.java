package com.chillycheesy.modulo.commands.operator.natif;

import com.chillycheesy.modulo.ModuloAPI;
import com.chillycheesy.modulo.commands.CommandContainer;
import com.chillycheesy.modulo.commands.CommandFlow;
import com.chillycheesy.modulo.commands.CommandManager;
import com.chillycheesy.modulo.commands.CommandProcessor;
import com.chillycheesy.modulo.commands.builder.CommandFlowBuilder;
import com.chillycheesy.modulo.commands.operator.BetweenOperator;
import com.chillycheesy.modulo.commands.operator.Operation;
import com.chillycheesy.modulo.commands.operator.OperatorListener;
import com.chillycheesy.modulo.commands.operator.builder.Operator;
import com.chillycheesy.modulo.modules.Module;
import com.chillycheesy.modulo.utils.Priority;
import com.chillycheesy.modulo.utils.exception.CommandException;

@Operator(Priority.EPIC)
public class ParenthesesOperator extends BetweenOperator implements OperatorListener {

    @Override
    public CommandFlow onOperate(Module module, CommandFlow left, CommandFlow center, CommandFlow right) throws CommandException {
        final String content = center.getContent().replaceAll("^\\(|\\)$", "");
        center.setContent(content);
        final CommandContainer commandContainer = ModuloAPI.getCommand();
        final CommandManager manager = commandContainer.getCommandManager();
        final CommandProcessor processor = manager.getProcessor();
        return CommandFlowBuilder.combine(left.getAliasManager(), left, processor.execute(module, center), right);
    }

    @Override
    public Operation findOperatorMatch(CommandFlow flux) {
        return super.findOperatorMatch(flux, '(', ')');
    }

}
