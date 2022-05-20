package com.chillycheesy.modulo.commands.operator.natif;

import com.chillycheesy.modulo.commands.CommandFlow;
import com.chillycheesy.modulo.commands.builder.CommandFlowBuilder;
import com.chillycheesy.modulo.commands.operator.Operation;
import com.chillycheesy.modulo.commands.operator.OperatorFinder;
import com.chillycheesy.modulo.commands.operator.OperatorListener;
import com.chillycheesy.modulo.modules.Module;
import com.chillycheesy.modulo.utils.Priority;
import com.chillycheesy.modulo.utils.exception.CommandException;
import com.chillycheesy.modulo.commands.operator.builder.Operator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Operator that return the opposite of the given parameter.
 * This operator only work with a boolean after the symbol "!"
 * Exemple:
 * <ul>
 *     <li>!true = false</li>
 *     <li>!false = true</li>
 * </ul>
 */
@Operator(Priority.NEUTRAL)
public class NotOperator implements OperatorListener, OperatorFinder {

    public static final String OPERATOR_REGEX = "(\\!)";

    protected CommandFlow applyOperation(CommandFlow left, CommandFlow center, CommandFlow right, String operator) {
        String BOOLEAN_REGEX = "(true|false)";
        final boolean rightBoolean = Boolean.parseBoolean(CommandFlowBuilder.extractFromFlux(right, "^" + BOOLEAN_REGEX));
        final CommandFlow flux = CommandFlowBuilder.combine(left.getAliasManager(), left, center, right);
        flux.setContent(flux.getContent().replaceFirst(operator + "\\s*" + BOOLEAN_REGEX," " + !rightBoolean));
        return flux;
    }

    @Override
    public Operation findOperatorMatch(CommandFlow flux) {
        final Pattern pattern = Pattern.compile("(?<!\\\\)(\\!\\s*(true|false))");
        final Matcher matcher = pattern.matcher(flux.getContent());
        if (matcher.find()) {
            final String content = flux.getContent();
            final CommandFlow left = new CommandFlow(content.substring(0, matcher.start()).trim(), flux.getAliasManager());
            final CommandFlow center = new CommandFlow(content.substring(matcher.start(), matcher.start() + 1).trim(), flux.getAliasManager());
            final CommandFlow right = new CommandFlow(content.substring(matcher.start() + 1).trim(), flux.getAliasManager());
            return new Operation(left, center, right, this);
        }
        return null;
    }

    @Override
    public CommandFlow onOperate(Module module, CommandFlow left, CommandFlow center, CommandFlow right) throws CommandException {
        return this.applyOperation(left, center, right, OPERATOR_REGEX);
    }
}