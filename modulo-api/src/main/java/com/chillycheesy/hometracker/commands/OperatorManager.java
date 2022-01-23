package com.chillycheesy.hometracker.commands;

import com.chillycheesy.hometracker.modules.Module;
import com.chillycheesy.hometracker.utils.Manager;

import java.util.List;

public class OperatorManager extends Manager<Operator> {

    public boolean registerItemToBuild(Module module, Object item) {
        final Operator operator = OperatorBuilder.build(item);
        return super.registerItem(module, operator);
    }

    public boolean registerItemToBuild(Module module, Object...items) {
        boolean success = true;
        for (Object item : items)
            if (!this.registerItemToBuild(module, item))
                success = false;
        return success;
    }

    public CommandFlux applyOperators(CommandFlux flux) {
        for (Operator operator : getSortedOperators()) {
            final OperatorFinder finder = operator.getFinder();
            final Operation operation = finder.findOperatorMatch(flux);
            if (operation != null)
                flux = applyOperator(operator, operation);
        }
        return flux;
    }

    private CommandFlux applyOperator(Operator operator, Operation operation) {
        final CommandFlux flux = operation.apply();
        final OperatorFinder finder = operator.getFinder();
        final Operation newOperation = finder.findOperatorMatch(flux);
        return newOperation != null ? applyOperator(operator, newOperation) : flux;
    }

    private List<Operator> getSortedOperators() {
        final List<Operator> sortedEntries = super.getAllItems();
        sortedEntries.sort((e1, e2) -> e2.getPriority() - e1.getPriority());
        return sortedEntries;
    }

}
