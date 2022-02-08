package com.chillycheesy.hometracker.commands.operator.natif;

import com.chillycheesy.hometracker.commands.CommandFlow;
import com.chillycheesy.hometracker.commands.builder.CommandFlowBuilder;
import com.chillycheesy.hometracker.commands.operator.Operation;
import com.chillycheesy.hometracker.commands.operator.OperatorFinder;
import com.chillycheesy.hometracker.commands.operator.OperatorListener;
import com.chillycheesy.hometracker.commands.operator.builder.Operator;
import com.chillycheesy.hometracker.modules.Module;
import com.chillycheesy.hometracker.utils.Priority;
import com.chillycheesy.hometracker.utils.exception.CommandException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

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