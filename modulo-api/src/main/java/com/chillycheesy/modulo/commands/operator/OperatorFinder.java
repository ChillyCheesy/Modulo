package com.chillycheesy.modulo.commands.operator;

import com.chillycheesy.modulo.commands.CommandFlow;

/**
 * This class is used to find the operator inside a {@link CommandFlow}.
 */
public interface OperatorFinder {
    /**
     * Finds the operator in the given {@link CommandFlow}.
     * @param flux the {@link CommandFlow} to search.
     * @return the operator, or null if not found.
     */
    Operation findOperatorMatch(CommandFlow flux);
}
