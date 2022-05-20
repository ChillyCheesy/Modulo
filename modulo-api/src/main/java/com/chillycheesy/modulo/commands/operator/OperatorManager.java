package com.chillycheesy.modulo.commands.operator;

import com.chillycheesy.modulo.commands.CommandFlow;
import com.chillycheesy.modulo.commands.operator.builder.OperatorBuilder;
import com.chillycheesy.modulo.modules.Module;
import com.chillycheesy.modulo.utils.Manager;
import com.chillycheesy.modulo.utils.exception.CommandException;

import java.util.List;

/**
 * Manager for the Operators.
 */
public class OperatorManager extends Manager<Operator> {

    /**
     * Register an Operator to be built by the {@link OperatorBuilder}.
     * @param module The module that is registering the Operator.
     * @param item The item to be registered.
     * @return True if the item was registered.
     */
    public boolean registerItemToBuild(Module module, Object item) {
        final Operator operator = OperatorBuilder.build(item);
        return super.registerItem(module, operator);
    }

    /**
     * Register Operators to be built by the {@link OperatorBuilder}.
     * @param module The module that is registering the Operator.
     * @param items The items to be registered.
     * @return True if the items was registered.
     */
    public boolean registerItemToBuild(Module module, Object...items) {
        boolean success = true;
        for (Object item : items)
            if (!this.registerItemToBuild(module, item))
                success = false;
        return success;
    }

    /**
     * Apply the Operators to the CommandFlow.
     * @param caller The module that is calling the Operators.
     * @param flow The CommandFlow to be operated on.
     * @return The CommandFlow after the Operators have been applied.
     * @throws CommandException If an error occurs.
     */
    public CommandFlow applyOperators(Module caller, CommandFlow flow) throws CommandException {
        for (Operator operator : getSortedOperators()) {
            final OperatorFinder finder = operator.getFinder();
            final Operation operation = finder.findOperatorMatch(flow);
            if (operation != null) flow = applyOperators(caller, applyOperator(caller, operator, operation));
        }
        return flow;
    }
    private CommandFlow applyOperator(Module caller, Operator operator, Operation operation) throws CommandException {
        final CommandFlow flux = operation.apply(caller);
        final OperatorFinder finder = operator.getFinder();
        final Operation newOperation = finder.findOperatorMatch(flux);
        return newOperation != null ? applyOperator(caller, operator, newOperation) : flux;
    }

    private List<Operator> getSortedOperators() {
        final List<Operator> sortedEntries = super.getAllItems();
        sortedEntries.sort((e1, e2) -> e2.getPriority() - e1.getPriority());
        return sortedEntries;
    }

}
