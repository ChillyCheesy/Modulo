package com.chillycheesy.modulo.commands.operator;

import com.chillycheesy.modulo.commands.CommandFlow;

public interface OperatorFinder {
    Operation findOperatorMatch(CommandFlow flux);
}
