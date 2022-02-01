package com.chillycheesy.hometracker.commands.operator.natif;

import com.chillycheesy.hometracker.commands.AliasManager;
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

@Operator(Priority.HIGH)
public class AliasOperator implements OperatorListener, OperatorFinder {

    @Override
    public CommandFlux onOperate(Module module, CommandFlux left, CommandFlux center, CommandFlux right) throws CommandException {
        final AliasManager aliasManager = center.getAliasManager();
        final String alias = aliasManager.getValue(center.getContent());
        center.setContent(alias);
        return FluxBuilder.combine(left.getAliasManager(), left, center, right);
    }

    @Override
    public Operation findOperatorMatch(CommandFlux flux) {
        final AliasManager aliasManager = flux.getAliasManager();
        for (String alias : aliasManager.getAliases()) {
            final Pattern pattern = Pattern.compile("(?<!\\\\)" + alias + "(?!\\w)");
            final Matcher matcher = pattern.matcher(flux.getContent());
            if (matcher.find()) {
                return createOperation(aliasManager, flux.getContent(), matcher.start(), matcher.end());
            }
        }
        return null;
    }

    private Operation createOperation(AliasManager aliasManager, String content, int start, int end) {
        final int startContent = 0, endContent = content.length();
        final CommandFlux left = FluxBuilder.create(content.substring(startContent, start), aliasManager);
        final CommandFlux center = FluxBuilder.create(content.substring(start, end), aliasManager);
        final CommandFlux right = FluxBuilder.create(content.substring(end, endContent), aliasManager);
        return new Operation(left, center, right, this);
    }
}
