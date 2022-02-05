package com.chillycheesy.hometracker.commands.operator.natif;

import com.chillycheesy.hometracker.commands.CommandFlux;
import com.chillycheesy.hometracker.commands.FluxBuilder;
import com.chillycheesy.hometracker.commands.operator.Operation;
import com.chillycheesy.hometracker.commands.operator.builder.Operator;
import com.chillycheesy.hometracker.commands.operator.builder.OperatorFindByRegex;
import com.chillycheesy.hometracker.commands.operator.OperatorListener;
import com.chillycheesy.hometracker.modules.Module;
import com.chillycheesy.hometracker.utils.Priority;
import com.chillycheesy.hometracker.utils.exception.CommandException;

@Operator(Priority.HIGH)
@OperatorFindByRegex("(\\(.*[^\\\\]+\\)\\s*[^\\\\]?:>|<:\\s*[^\\\\]?\\(.*[^\\\\]+\\))")
public class InjectorOperator implements OperatorListener {

    @Override
    public CommandFlux onOperate(Module module, CommandFlux left, CommandFlux center, CommandFlux right) throws CommandException {
        final ParenthesesOperator parenthesesOperator = new ParenthesesOperator();
        final Operation operation = parenthesesOperator.findOperatorMatch(center);
        final String toInject = operation.getCenter().getContent().trim()
                .replaceAll("^\\(|\\)$", "")
                .replaceAll("([^;]$)", "$1;");
        final String centerContent = center.getContent();
        CommandFlux toReceive, newLeft, newRight;
        if (centerContent.endsWith(":>")) {
            toReceive = right;
            newLeft = left;
            newRight = FluxBuilder.create("");
        } else {
            toReceive = left;
            newLeft = FluxBuilder.create("");
            newRight = right;
        }
        return FluxBuilder.combine(
                left.getAliasManager(),
                newLeft,
                FluxBuilder.create("{" + toInject + toReceive.getContent() + "}", toReceive.getAliasManager()),
                newRight
        );
    }



}