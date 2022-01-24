package com.chillycheesy.hometracker.commands.operator;

import com.chillycheesy.hometracker.commands.CommandFlux;

public interface OperatorListener {
    CommandFlux onOperate(CommandFlux left, CommandFlux center, CommandFlux right);
}
