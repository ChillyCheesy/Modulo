package com.chillycheesy.hometracker.commands.operator.natif;

import com.chillycheesy.hometracker.commands.CommandFlux;
import com.chillycheesy.hometracker.commands.FluxBuilder;
import com.chillycheesy.hometracker.commands.operator.OperatorListener;
import com.chillycheesy.hometracker.commands.operator.builder.Operator;
import com.chillycheesy.hometracker.commands.operator.builder.OperatorFindByRegex;
import com.chillycheesy.hometracker.modules.Module;
import com.chillycheesy.hometracker.utils.Priority;
import com.chillycheesy.hometracker.utils.exception.CommandException;


@Operator(Priority.MAJOR)
@OperatorFindByRegex("(==)")
public class EqualsOperator implements OperatorListener {

    protected final String STRING_REGEX = "\\w+";

    @Override
    public CommandFlux onOperate(Module module, CommandFlux left, CommandFlux center, CommandFlux right) throws CommandException {
        final String leftString = FluxBuilder.extractFromFlux(left, STRING_REGEX + "$", "");
        final String rightString = FluxBuilder.extractFromFlux(right, "^" + STRING_REGEX, "");
        final CommandFlux flux = FluxBuilder.combine(left.getAliasManager(), left, center, right);
        System.out.println(leftString + " " + rightString);
        flux.setContent(flux.getContent().replaceFirst(STRING_REGEX + "\\s*" + "==" + "\\s*" + STRING_REGEX, "" + this.equals(leftString, rightString)));
        return flux;
    }

    private boolean equals(Object left, Object right) {
        return left.equals(right);
    }
}