package com.chillycheesy.hometracker.commands.operator.natif;

import com.chillycheesy.hometracker.commands.CommandFlux;
import com.chillycheesy.hometracker.commands.FluxBuilder;
import com.chillycheesy.hometracker.commands.operator.Operation;
import com.chillycheesy.hometracker.commands.operator.OperatorFinder;
import com.chillycheesy.hometracker.commands.operator.OperatorListener;
import com.chillycheesy.hometracker.commands.operator.builder.Operator;
import com.chillycheesy.hometracker.modules.Module;
import com.chillycheesy.hometracker.utils.Priority;
import com.chillycheesy.hometracker.utils.exception.CommandException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


@Operator(Priority.IMPORTANT)
public class NotOperator implements OperatorListener, OperatorFinder {

    public static final String OPERATOR_REGEX = "(\\!)";
    private final String BOOLEAN_REGEX= "(true|false)";

    protected CommandFlux applyOperation(CommandFlux left, CommandFlux center, CommandFlux right, String operator) {
        final boolean rightBoolean = Boolean.parseBoolean(FluxBuilder.extractFromFlux(right, "^" + BOOLEAN_REGEX));
        final CommandFlux flux = FluxBuilder.combine(left.getAliasManager(), left, center, right);
        System.out.println(left.getContent() + operator + right.getContent());
        flux.setContent(flux.getContent().replaceFirst(operator + "\\s*" + BOOLEAN_REGEX," " + !rightBoolean));
        return flux;
    }

    @Override
    public Operation findOperatorMatch(CommandFlux flux) {
        final Pattern pattern = Pattern.compile("(?<!\\\\)(\\!\\s*(true|false))");
        final Matcher matcher = pattern.matcher(flux.getContent());
        if (matcher.find()) {
            final String content = flux.getContent();
            final CommandFlux left = new CommandFlux(content.substring(0, matcher.start()).trim(), flux.getAliasManager());
            final CommandFlux center = new CommandFlux(content.substring(matcher.start(), matcher.start() + 1).trim(), flux.getAliasManager());
            final CommandFlux right = new CommandFlux(content.substring(matcher.start() + 1, content.length()).trim(), flux.getAliasManager());
            return new Operation(left, center, right, this);
        }
        return null;
    }

    @Override
    public CommandFlux onOperate(Module module, CommandFlux left, CommandFlux center, CommandFlux right) throws CommandException {
        return this.applyOperation(left, center, right, OPERATOR_REGEX);
    }
}