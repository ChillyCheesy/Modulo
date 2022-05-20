package com.chillycheesy.modulo.commands.operator.natif;

import com.chillycheesy.modulo.commands.CommandFlow;
import com.chillycheesy.modulo.commands.builder.CommandFlowBuilder;
import com.chillycheesy.modulo.commands.operator.OperatorListener;
import com.chillycheesy.modulo.commands.operator.builder.Operator;
import com.chillycheesy.modulo.commands.operator.builder.OperatorFindByRegex;
import com.chillycheesy.modulo.modules.Module;
import com.chillycheesy.modulo.utils.Priority;
import com.chillycheesy.modulo.utils.exception.CommandException;

@Operator(Priority.MYSTIC)
@OperatorFindByRegex("=>")
public class CreateStrictAliasOperator implements OperatorListener {

    @Override
    public CommandFlow onOperate(Module module, CommandFlow left, CommandFlow center, CommandFlow right) throws CommandException {
        final String leftContent = left.getContent();
        final String rightContent = right.getContent();
        return CommandFlowBuilder.create(center.getAliasManager(), "alias '" + leftContent + "' '" + rightContent + "'");
    }

}
