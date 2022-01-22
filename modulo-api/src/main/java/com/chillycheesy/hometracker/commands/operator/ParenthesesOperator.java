package com.chillycheesy.hometracker.commands.operator;

import com.chillycheesy.hometracker.ModuloAPI;
import com.chillycheesy.hometracker.commands.*;

public class ParenthesesOperator extends Operator implements OperatorListener {

    public ParenthesesOperator() {
        super(Operator.DIVINE, null);
        super.setListener(this);
    }

    @Override
    public CommandFlux onOperate(CommandFlux left, CommandFlux center, CommandFlux right) {
        final String content = center.getContent().replaceAll("^\\(|\\)$", "");
        center.setContent(content);
        final CommandContainer commandContainer = ModuloAPI.getCommand();
        final CommandManager manager = commandContainer.getCommandManager();
        final CommandProcessor processor = manager.getProcessor();
        return FluxBuilder.combine(left, processor.execute(center), right);
    }

    @Override
    public Operation findOperatorMatch(CommandFlux flux) {
        if (flux != null) {
            final String content = flux.getContent();
            int start = content.indexOf('('), open = 1;
            if (start > -1 && start < content.length() - 1) {
                for (int i = start + 1 ; i < content.length() ; ++i) {
                    char c = content.charAt(i);
                    if (c == '(') ++open;
                    else if (c == ')') --open;
                    if (open == 0) return createOperation(content, start, i);
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
