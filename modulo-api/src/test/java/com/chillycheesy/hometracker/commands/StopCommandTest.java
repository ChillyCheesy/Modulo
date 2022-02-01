package com.chillycheesy.hometracker.commands;

import com.chillycheesy.hometracker.ModuloAPI;
import com.chillycheesy.hometracker.commands.operator.*;
import com.chillycheesy.hometracker.commands.operator.natif.ParenthesesOperator;
import com.chillycheesy.hometracker.commands.operator.natif.PlusOperator;
import com.chillycheesy.hometracker.commands.operator.natif.SkipOperator;
import com.chillycheesy.hometracker.utils.exception.CommandException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class StopCommandTest {

    private CommandManager commandManager;

    @BeforeEach
    public final void beforeEach() {
        final OperatorManager operatorManager = ModuloAPI.getCommand().getOperatorManager();
        operatorManager.registerItemToBuild(null,
                new ParenthesesOperator(),
                new SkipOperator(),
                new PlusOperator()
        );
        commandManager = ModuloAPI.getCommand().getCommandManager();
    }

    @Test
    public final void testRunCommand() throws CommandException {
        // final CommandFlux flux = commandManager.applyCommand("command arg1 '6 + 7' \"6 + 7\"");

    }

}
