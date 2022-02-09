package com.chillycheesy.modulo.commands.operator.natif;

import com.chillycheesy.modulo.commands.AliasManager;
import com.chillycheesy.modulo.commands.CommandFlow;
import com.chillycheesy.modulo.commands.builder.CommandFlowBuilder;
import com.chillycheesy.modulo.commands.operator.BetweenOperator;
import com.chillycheesy.modulo.commands.operator.Operation;
import com.chillycheesy.modulo.commands.operator.OperatorListener;
import com.chillycheesy.modulo.modules.Module;
import com.chillycheesy.modulo.utils.Priority;
import com.chillycheesy.modulo.commands.operator.builder.Operator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Operator(Priority.DIVINE)
public class SkipOperator extends BetweenOperator implements OperatorListener {

    @Override
    public CommandFlow onOperate(Module module, CommandFlow left, CommandFlow center, CommandFlow right) {
        center.setContent(format(center.getContent().replaceAll("^'|'$", "")));
        return CommandFlowBuilder.combine(left.getAliasManager(), left, center, right);
    }

    private String format(String content) {
        final StringBuilder result = new StringBuilder("\"");
        for (int i = 0 ; i < content.length() ; ++i) {
            result.append("\\").append(content.charAt(i));
        }
        return result.append("\"").toString();
    }

    @Override
    public Operation findOperatorMatch(CommandFlow flux) {
        if (flux != null) {
            final String content = flux.getContent();
            final Pattern pattern = Pattern.compile("(?<!\\\\)'");
            final Matcher matcher = pattern.matcher(content);
            if (matcher.find()) {
                int start = matcher.start();
                if (start > -1 && start < content.length() - 1) {
                    for (int i = start + 1 ; i < content.length() ; ++i) {
                        char c = content.charAt(i);
                        if (c == '\\') ++i;
                        else if (c == '\'') return createOperation(flux.getAliasManager(), content, start, i);
                    }
                }
            }
        }
        return null;
    }

    private Operation createOperation(AliasManager aliasManager, String content, int start, int end) {
        final int startContent = 0, endContent = content.length();
        final CommandFlow left = CommandFlowBuilder.create(aliasManager, content.substring(startContent, start));
        final CommandFlow center = CommandFlowBuilder.create(aliasManager, content.substring(start, end + 1));
        final CommandFlow right = CommandFlowBuilder.create(aliasManager, content.substring(end + 1, endContent));
        return new Operation(left, center, right, this);
    }
}
