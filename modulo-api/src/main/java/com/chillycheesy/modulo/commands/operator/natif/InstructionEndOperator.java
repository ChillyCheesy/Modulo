package com.chillycheesy.modulo.commands.operator.natif;

import com.chillycheesy.modulo.ModuloAPI;
import com.chillycheesy.modulo.commands.CommandFlow;
import com.chillycheesy.modulo.commands.CommandProcessor;
import com.chillycheesy.modulo.commands.builder.CommandFlowBuilder;
import com.chillycheesy.modulo.commands.operator.Operation;
import com.chillycheesy.modulo.commands.operator.OperatorFinder;
import com.chillycheesy.modulo.commands.operator.OperatorListener;
import com.chillycheesy.modulo.commands.operator.builder.Operator;
import com.chillycheesy.modulo.modules.Module;
import com.chillycheesy.modulo.utils.Priority;
import com.chillycheesy.modulo.utils.exception.CommandException;

@Operator(Priority.LEGENDARY)
public class InstructionEndOperator implements OperatorFinder, OperatorListener {

    @Override
    public Operation findOperatorMatch(CommandFlow flux) {
        final String content = flux.getContent();
        int openParentheses = 0, openBrackets = 0, openBraces = 0;
        boolean skipSimple = false, skipDouble = false;
        for (int i = 0 ; i < content.length() ; ++i) {
            char c = content.charAt(i);
            if (c == '\\') ++i;
            else if (c == '(') ++openParentheses;
            else if (c == ')') --openParentheses;
            else if (c == '[') ++openBrackets;
            else if (c == ']') --openBrackets;
            else if (c == '{') ++openBraces;
            else if (c == '}') --openBraces;
            else if (c == '\'') skipSimple = !skipSimple;
            else if (c == '"') skipDouble = !skipDouble;
            else if (c == ';' && !skipSimple && !skipDouble && openParentheses + openBrackets + openBraces == 0)
                return createOperation(flux, i);
        }
        return null;
    }

    private Operation createOperation(CommandFlow flux, int index) {
        final String content = flux.getContent();
        final int startContent = 0, endContent = content.length();
        final CommandFlow left = CommandFlowBuilder.create(flux.getAliasManager(), content.substring(startContent, index));
        final CommandFlow center = CommandFlowBuilder.create(flux.getAliasManager(), content.substring(index, index + 1));
        final CommandFlow right = CommandFlowBuilder.create(flux.getAliasManager(), content.substring(index + 1, endContent));
        return new Operation(left, center, right, this);
    }

    @Override
    public CommandFlow onOperate(Module module, CommandFlow left, CommandFlow center, CommandFlow right) throws CommandException {
        final CommandProcessor commandProcessor = ModuloAPI.getCommand().getCommandManager().getProcessor();
        final CommandFlow leftResult = commandProcessor.execute(module, left);
        right.setAliasManager(leftResult.getAliasManager());
        return right.getContent().equals("") ? leftResult : right;
    }
}
