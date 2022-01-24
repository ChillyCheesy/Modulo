package com.chillycheesy.hometracker.commands.operator;

import com.chillycheesy.hometracker.commands.CommandFlux;

public interface OperatorFinder {
    Operation findOperatorMatch(CommandFlux flux);
}
