package com.chillycheesy.modulo.commands.operator.natif;

import com.chillycheesy.modulo.commands.CommandFlow;
import com.chillycheesy.modulo.commands.operator.NumberOperator;
import com.chillycheesy.modulo.commands.operator.Operation;
import com.chillycheesy.modulo.commands.operator.OperatorFinder;
import com.chillycheesy.modulo.commands.operator.builder.Operator;
import com.chillycheesy.modulo.modules.Module;
import com.chillycheesy.modulo.utils.Priority;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Operator that execute a subtraction with the two parameter
 * This operator need number before and after the symbol "-"
 * Exemple :
 *          5 - 6 => -1
 *          -3 - 2 => -5
 */
@Operator(Priority.COMMON)
public class MinusOperator extends NumberOperator implements OperatorFinder {

    @Override
    public CommandFlow onOperate(Module module, CommandFlow left, CommandFlow center, CommandFlow right) {
        return super.applyOperation(left, center, right, "-", this::minus);
    }

    private double minus(double a, double b) {
        return a - b;
    }


    @Override
    public Operation findOperatorMatch(CommandFlow flux) {
        final Pattern pattern = Pattern.compile("\\d+\\.?\\d*\\s*(?<!\\\\)-");
        final Matcher matcher = pattern.matcher(flux.getContent());
        if (matcher.find()) {
            final String content = flux.getContent();
            final int startContent = 0, start = matcher.end() - 1, end = matcher.end(), endContent = content.length();
            final CommandFlow left = new CommandFlow(content.substring(startContent, start).trim(), flux.getAliasManager());
            final CommandFlow center = new CommandFlow(content.substring(start, end).trim(), flux.getAliasManager());
            final CommandFlow right = new CommandFlow(content.substring(end, endContent).trim(), flux.getAliasManager());
            return new Operation(left, center, right, this);
        }
        return null;
    }
}
