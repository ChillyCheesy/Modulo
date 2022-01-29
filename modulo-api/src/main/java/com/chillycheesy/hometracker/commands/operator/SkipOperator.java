package com.chillycheesy.hometracker.commands.operator;

import com.chillycheesy.hometracker.commands.*;
import com.chillycheesy.hometracker.modules.Module;
import com.chillycheesy.hometracker.utils.Priority;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Operator(Priority.DIVINE)
public class SkipOperator implements OperatorListener, OperatorFinder {

    @Override
    public CommandFlux onOperate(Module module, CommandFlux left, CommandFlux center, CommandFlux right) {
        center.setContent(format(center.getContent().replaceAll("^'|'$", "")));
        return FluxBuilder.combine(left, center, right);
    }

    private String format(String content) {
        final StringBuilder result = new StringBuilder("\"");
        for (int i = 0 ; i < content.length() ; ++i) {
            result.append("\\").append(content.charAt(i));
        }
        return result.append("\"").toString();
    }

    @Override
    public Operation findOperatorMatch(CommandFlux flux) {
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
                        else if (c == '\'') return createOperation(content, start, i);
                    }
                }
            }
        }
        return null;
    }

    private Operation createOperation(String content, int start, int end) {
        final int startContent = 0, endContent = content.length();
        final CommandFlux left = new CommandFlux(content.substring(startContent, start));
        final CommandFlux center = new CommandFlux(content.substring(start, end + 1));
        final CommandFlux right = new CommandFlux(content.substring(end + 1, endContent));
        return new Operation(left, center, right, this);
    }
}
