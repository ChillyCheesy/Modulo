package com.chillycheesy.modulo.commands.operator;

import com.chillycheesy.modulo.commands.AliasManager;
import com.chillycheesy.modulo.commands.CommandFlow;
import com.chillycheesy.modulo.commands.builder.CommandFlowBuilder;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Utils class to the creation of operator that takes two character and any value inside.
 */
public abstract class BetweenOperator implements OperatorFinder, OperatorListener {

    /**
     * Detects the operator in the given content.
     *
     * The operator is build inside two char and any value inside.
     * With the first char '&lt;' and the second char '&gt;'.
     *
     * Exemple: in the flow: 'all my ewok life is amazing &lt;I'm pretty &lt;sure&gt;&gt;'
     * the method catch &lt;I'm pretty &lt;sure&gt;&gt;.
     * @param flux the content to detect the operator.
     * @param firstChar the first char of the operator.
     * @param lastChar the last char of the operator.
     * @return the operator if found, null otherwise.
     */
    public Operation findOperatorMatch(CommandFlow flux, char firstChar, char lastChar) {
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
        final CommandFlow left = CommandFlowBuilder.create(aliasManager, content.substring(startContent, start));
        final CommandFlow center = CommandFlowBuilder.create(aliasManager, content.substring(start, end + 1));
        final CommandFlow right = CommandFlowBuilder.create(aliasManager, content.substring(end + 1, endContent));
        return new Operation(left, center, right, this);
    }

}
