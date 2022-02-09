package com.chillycheesy.modulo.commands.operator.natif;

import com.chillycheesy.modulo.commands.AliasManager;
import com.chillycheesy.modulo.commands.CommandFlow;
import com.chillycheesy.modulo.commands.builder.CommandFlowBuilder;
import com.chillycheesy.modulo.commands.operator.Operation;
import com.chillycheesy.modulo.commands.operator.OperatorFinder;
import com.chillycheesy.modulo.commands.operator.OperatorListener;
import com.chillycheesy.modulo.commands.operator.builder.Operator;
import com.chillycheesy.modulo.modules.Module;
import com.chillycheesy.modulo.utils.Priority;
import com.chillycheesy.modulo.utils.exception.CommandException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Operator(Priority.MAJOR)
public class AliasOperator implements OperatorListener, OperatorFinder {

    @Override
    public CommandFlow onOperate(Module module, CommandFlow left, CommandFlow center, CommandFlow right) throws CommandException {
        final AliasManager aliasManager = center.getAliasManager();
        final String alias = aliasManager.getValue(center.getContent());
        center.setContent(alias);
        return CommandFlowBuilder.combine(left.getAliasManager(), left, center, right);
    }

    @Override
    public Operation findOperatorMatch(CommandFlow flux) {
        final AliasManager aliasManager = flux.getAliasManager();
        for (String alias : aliasManager.getAliases()) {
            final Pattern pattern = Pattern.compile("(?<![\\\\\\w])(" + alias + ")(?!\\w)");
            final Matcher matcher = pattern.matcher(flux.getContent());
            if (matcher.find()) {
                return createOperation(aliasManager, flux.getContent(), matcher.start(), matcher.end());
            }
        }
        return null;
    }

    private Operation createOperation(AliasManager aliasManager, String content, int start, int end) {
        final int startContent = 0, endContent = content.length();
        final CommandFlow left = CommandFlowBuilder.create(aliasManager, content.substring(startContent, start));
        final CommandFlow center = CommandFlowBuilder.create(aliasManager, content.substring(start, end));
        final CommandFlow right = CommandFlowBuilder.create(aliasManager, content.substring(end, endContent));
        return new Operation(left, center, right, this);
    }
}
