package com.chillycheesy.hometracker.commands.operator;

import com.chillycheesy.hometracker.commands.CommandFlux;
import com.chillycheesy.hometracker.modules.Module;

public interface OperatorListener {
    CommandFlux onOperate(Module module, CommandFlux left, CommandFlux center, CommandFlux right);
}
