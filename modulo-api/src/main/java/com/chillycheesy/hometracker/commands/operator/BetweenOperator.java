package com.chillycheesy.hometracker.commands.operator;

import com.chillycheesy.hometracker.commands.AliasManager;
import com.chillycheesy.hometracker.commands.CommandFlux;
import com.chillycheesy.hometracker.commands.FluxBuilder;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class BetweenOperator implements OperatorFinder, OperatorListener {

    public Operation findOperatorMatch(CommandFlux flux, char firstChar, char lastChar) {
        if (flux != null) {
            final String content = flux.getContent();
            final String regex = String.format("(?<!\\\\)\\%s", firstChar);
            final Pattern pattern = Pattern.compile(regex);
            final Matcher matcher = pattern.matcher(content);
            if (matcher.find()) {
                int start = matcher.start(), open = 1;
                if (start > -1 && start < content.length() - 1) {
                    for (int i = start + 1 ; i < content.length() ; ++i) {
                        char c = content.charAt(i);
                        if (c == '\\') ++i;
                        else if (c == firstChar) ++open;
                        else if (c == lastChar) --open;
                        if (open == 0) return createOperation(flux.getAliasManager(), content, start, i);
                    }
                }
            }
        }
        return null;
    }

    private Operation createOperation(AliasManager aliasManager, String content, int start, int end) {
        final int startContent = 0, endContent = content.length();
        final CommandFlux left = FluxBuilder.create(aliasManager, content.substring(startContent, start));
        final CommandFlux center = FluxBuilder.create(aliasManager, content.substring(start, end + 1));
        final CommandFlux right = FluxBuilder.create(aliasManager, content.substring(end + 1, endContent));
        return new Operation(left, center, right, this);
    }

}
