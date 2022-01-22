package com.chillycheesy.hometracker.commands;

import com.chillycheesy.hometracker.utils.Manager;

import java.util.List;

public class OperatorManager extends Manager<Operator> {

    public CommandFlux applyOperators(CommandFlux flux) {
        for (Operator operator : getSortedOperators()) {
            final OperatorListener listener = operator.getListener();
            final Operation operation = listener.findOperatorMatch(flux);
            if (operation != null)
                flux = applyOperator(operator, operation);
        }
        return flux;
    }

    private CommandFlux applyOperator(Operator operator, Operation operation) {
        final CommandFlux flux = operation.apply();
        final OperatorListener listener = operator.getListener();
        final Operation newOperation = listener.findOperatorMatch(flux);
        return newOperation != null ? applyOperator(operator, newOperation) : flux;
    }

    private List<Operator> getSortedOperators() {
        final List<Operator> sortedEntries = super.getAllItems();
        sortedEntries.sort((e1, e2) -> e2.getPriority() - e1.getPriority());
        return sortedEntries;
    }

}
