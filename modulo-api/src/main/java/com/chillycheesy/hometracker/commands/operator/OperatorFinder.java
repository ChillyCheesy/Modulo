package com.chillycheesy.hometracker.commands.operator;

import com.chillycheesy.hometracker.commands.CommandFlow;

public interface OperatorFinder {
    Operation findOperatorMatch(CommandFlow flux);
}
