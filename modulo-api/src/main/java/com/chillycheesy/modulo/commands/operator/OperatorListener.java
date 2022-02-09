package com.chillycheesy.modulo.commands.operator;

import com.chillycheesy.modulo.commands.CommandFlow;
import com.chillycheesy.modulo.modules.Module;
import com.chillycheesy.modulo.utils.exception.CommandException;

public interface OperatorListener {
    CommandFlow onOperate(Module module, CommandFlow left, CommandFlow center, CommandFlow right) throws CommandException;
}
