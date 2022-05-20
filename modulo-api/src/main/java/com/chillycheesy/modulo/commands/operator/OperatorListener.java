package com.chillycheesy.modulo.commands.operator;

import com.chillycheesy.modulo.commands.CommandFlow;
import com.chillycheesy.modulo.modules.Module;
import com.chillycheesy.modulo.utils.exception.CommandException;

/**
 * This class is used to determine the action to execute when the operator operate on a {@link CommandFlow}.
 */
public interface OperatorListener {

    /**
     * This method is called when the operator operates on a {@link CommandFlow}.
     * @param module The {@link Module} that is being operated on.
     * @param left The left {@link CommandFlow} that is being operated on.
     * @param center The center {@link CommandFlow} that is being operated on.
     * @param right The right {@link CommandFlow} that is being operated on.
     * @return The {@link CommandFlow} that is the result of the operation.
     * @throws CommandException If an error occurs during the operation.
     */
    CommandFlow onOperate(Module module, CommandFlow left, CommandFlow center, CommandFlow right) throws CommandException;
}
