package com.chillycheesy.hometracker.commands.operator.natif;

import com.chillycheesy.hometracker.commands.CommandFlux;
import com.chillycheesy.hometracker.commands.FluxBuilder;
import com.chillycheesy.hometracker.commands.operator.Operation;
import com.chillycheesy.hometracker.commands.operator.OperatorFinder;
import com.chillycheesy.hometracker.commands.operator.builder.Operator;
import com.chillycheesy.hometracker.commands.operator.OperatorListener;
import com.chillycheesy.hometracker.modules.Module;
import com.chillycheesy.hometracker.utils.Priority;
import com.chillycheesy.hometracker.utils.exception.CommandException;

@Operator(Priority.EPIC)
// @OperatorFindByRegex("(\\(.*[^\\\\]+\\)\\s*[^\\\\]?:>)")
public class InjectorOperator implements OperatorListener, OperatorFinder {

    @Override
    public CommandFlux onOperate(Module module, CommandFlux left, CommandFlux center, CommandFlux right) throws CommandException {
        final ParenthesesOperator parenthesesOperator = new ParenthesesOperator();
        final Operation operation = parenthesesOperator.findOperatorMatch(center);
        final String toInject = operation.getCenter().getContent().trim()
                .replaceAll("^\\(|\\)$", "")
                .replaceAll("([^;]$)", "$0;");
        return FluxBuilder.combine(
                left.getAliasManager(),
                left,
                FluxBuilder.create("{" + toInject + right.getContent() + "}", right.getAliasManager())
        );
    }

    @Override
    public Operation findOperatorMatch(CommandFlux flux) {
        final Operation operation = Operation.buildFormRegex(flux, "(\\(.*[^\\\\]+\\)\\s*[^\\\\]?:>)", this);
        System.out.println(operation);
        return operation;
    }
}