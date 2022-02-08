package com.chillycheesy.hometracker.commands.operator.natif;

import com.chillycheesy.hometracker.commands.CommandFlow;
import com.chillycheesy.hometracker.commands.builder.CommandFlowBuilder;
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
    public CommandFlow onOperate(Module module, CommandFlow left, CommandFlow center, CommandFlow right) throws CommandException {
        final String leftContent = stringify(left);
        final String rightContent = stringify(right);
        return CommandFlowBuilder.create(center.getAliasManager(), "alias " + leftContent + " " + rightContent);
    }

    private String stringify(CommandFlow flux) {
        return flux.getContent().replaceAll("^[^\"].*[^\"]$", "\"$0\"");
    }

}
