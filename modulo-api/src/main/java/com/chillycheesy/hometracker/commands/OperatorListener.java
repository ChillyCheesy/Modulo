package com.chillycheesy.hometracker.commands;

public interface OperatorListener {
    CommandFlux onOperate(CommandFlux left, CommandFlux center, CommandFlux right);
}
