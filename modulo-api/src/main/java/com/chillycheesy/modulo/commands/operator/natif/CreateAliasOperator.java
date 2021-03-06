package com.chillycheesy.modulo.commands.operator.natif;

import com.chillycheesy.modulo.commands.CommandFlow;
import com.chillycheesy.modulo.commands.builder.CommandFlowBuilder;
import com.chillycheesy.modulo.commands.operator.OperatorListener;
import com.chillycheesy.modulo.commands.operator.builder.Operator;
import com.chillycheesy.modulo.commands.operator.builder.OperatorFindByRegex;
import com.chillycheesy.modulo.modules.Module;
import com.chillycheesy.modulo.utils.Priority;
import com.chillycheesy.modulo.utils.exception.CommandException;

@Operator(Priority.COMMON)
@OperatorFindByRegex("=")
public class CreateAliasOperator implements OperatorListener {

    @Override
    public CommandFlow onOperate(Module module, CommandFlow left, CommandFlow center, CommandFlow right) throws CommandException {
        final String leftContent = stringify(left);
        final String rightContent = stringify(right);
        return CommandFlowBuilder.create(center.getAliasManager(), "alias " + leftContent + " " + rightContent);
    }

    private String stringify(CommandFlow flux) {
        return flux.getContent().replaceAll("^[^\"].*[^\"]$", "\"$0\"");
    }

}
