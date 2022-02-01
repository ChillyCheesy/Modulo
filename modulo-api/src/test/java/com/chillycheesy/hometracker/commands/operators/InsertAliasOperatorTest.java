package com.chillycheesy.hometracker.commands.operators;

import com.chillycheesy.hometracker.ModuloAPI;
import com.chillycheesy.hometracker.commands.CommandFlux;
import com.chillycheesy.hometracker.commands.CommandManager;
import com.chillycheesy.hometracker.commands.natif.EchoCommand;
import com.chillycheesy.hometracker.commands.operator.natif.InsertAliasOperator;
import com.chillycheesy.hometracker.commands.operator.natif.ParenthesesOperator;
import com.chillycheesy.hometracker.utils.exception.CommandException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class InsertAliasOperatorTest {
    private CommandManager commandManager;

    @BeforeEach
    public final void beforeEach() {
        commandManager = ModuloAPI.getCommand().getCommandManager();
        ModuloAPI.getCommand().getOperatorManager().registerItemToBuild(null, new ParenthesesOperator(), new InsertAliasOperator());
        commandManager.registerItemToBuild(null, new EchoCommand());
    }

    @AfterEach
    public final void afterEach() {
        ModuloAPI.getCommand().getOperatorManager().removeAllItems(null);
        ModuloAPI.getCommand().getCommandManager().removeAllItems(null);
    }

    @Test
    public final void testWithNoAlias() throws CommandException {
        final String line = "echo I Love ewoks";
        final CommandFlux flux = commandManager.applyCommand(line);
        assertEquals("I Love ewoks", flux.getContent());
    }
}
