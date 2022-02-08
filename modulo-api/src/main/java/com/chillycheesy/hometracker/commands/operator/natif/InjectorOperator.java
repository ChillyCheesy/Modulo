package com.chillycheesy.hometracker.commands.operator.natif;

import com.chillycheesy.hometracker.commands.CommandFlow;
import com.chillycheesy.hometracker.commands.builder.CommandFlowBuilder;
import com.chillycheesy.hometracker.commands.operator.builder.Operator;
import com.chillycheesy.hometracker.commands.operator.OperatorListener;
import com.chillycheesy.hometracker.commands.operator.builder.OperatorFindByRegex;
import com.chillycheesy.hometracker.modules.Module;
import com.chillycheesy.hometracker.utils.Priority;
import com.chillycheesy.hometracker.utils.exception.CommandException;

@Operator(Priority.HIGH)
@OperatorFindByRegex("((\".*[^\\\\]+\"|\\w+)\\s*[^\\\\]?:>)")
public class InjectorOperator implements OperatorListener {

    @Override
    public CommandFlow onOperate(Module module, CommandFlow left, CommandFlow center, CommandFlow right) throws CommandException {
        final String toInject = center.getContent().trim()
                .replaceAll("^\"|(\"\\s*[^\\\\]?:>)$", "")
                .replaceAll("([^;]$)", "$0;");
        left.setContent(left.getContent().replaceAll("\\S$", "$0 "));
        return CommandFlowBuilder.combine(
                left.getAliasManager(),
                left,
                CommandFlowBuilder.create(right.getAliasManager(), "{" + toInject + right.getContent() + "}")
        );
    }
}