package com.chillycheesy.hometracker.commands;

import com.chillycheesy.hometracker.ModuloAPI;
import com.chillycheesy.hometracker.commands.operator.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class StopCommandTest {

    private CommandManager commandManager;

    @BeforeEach
    public final void beforeEach() {
        final OperatorManager operatorManager = ModuloAPI.getCommand().getOperatorManager();
        operatorManager.registerItemToBuild(null,
                new ParenthesesOperator()
        );
        commandManager = ModuloAPI.getCommand().getCommandManager();
    }

    @Test
    public final void testRunCommand() {
        final CommandFlux flux = commandManager.applyCommand("command arg1 \"arg2 arg2\" \"arg3 arg3\"");

    }

}
