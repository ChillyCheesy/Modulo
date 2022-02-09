package com.chillycheesy.modulo.commands.operator.natif;

import com.chillycheesy.modulo.commands.CommandFlow;
import com.chillycheesy.modulo.commands.builder.CommandFlowBuilder;
import com.chillycheesy.modulo.commands.operator.OperatorListener;
import com.chillycheesy.modulo.commands.operator.builder.Operator;
import com.chillycheesy.modulo.commands.operator.builder.OperatorFindByRegex;
import com.chillycheesy.modulo.modules.Module;
import com.chillycheesy.modulo.utils.Priority;
import com.chillycheesy.modulo.utils.exception.CommandException;

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