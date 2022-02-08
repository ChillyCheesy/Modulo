package com.chillycheesy.hometracker.commands.operator;

import com.chillycheesy.hometracker.commands.CommandFlow;
import com.chillycheesy.hometracker.modules.Module;
import com.chillycheesy.hometracker.utils.exception.CommandException;

public interface OperatorListener {
    CommandFlow onOperate(Module module, CommandFlow left, CommandFlow center, CommandFlow right) throws CommandException;
}
