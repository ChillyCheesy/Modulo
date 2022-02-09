package com.chillycheesy.modulo.commands.operators;

import com.chillycheesy.modulo.ModuloAPI;
import com.chillycheesy.modulo.commands.CommandFlow;
import com.chillycheesy.modulo.commands.CommandManager;
import com.chillycheesy.modulo.commands.natif.EchoCommand;
import com.chillycheesy.modulo.commands.operator.natif.AliasOperator;
import com.chillycheesy.modulo.commands.operator.natif.ParenthesesOperator;
import com.chillycheesy.modulo.utils.exception.CommandException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AliasOperatorTest {

    private CommandManager commandManager;

    @BeforeEach
    public final void beforeEach() {
        commandManager = ModuloAPI.getCommand().getCommandManager();
        ModuloAPI.getCommand().getOperatorManager().registerItemToBuild(null, new AliasOperator(), new ParenthesesOperator());
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
        final CommandFlow flux = commandManager.applyCommand(line);
        assertEquals("I Love ewoks", flux.getContent());
    }

    @Test
    public final void testWithAliasEnd() throws CommandException {
        ModuloAPI.getCommand().getMainAliasManager().registerAlias("wookies", "ewoks");
        final String line = "echo I Love wookies";
        final CommandFlow flux = commandManager.applyCommand(line);
        assertEquals("I Love ewoks", flux.getContent());
    }

    @Test
    public final void testWithAliasCenter() throws CommandException {
        ModuloAPI.getCommand().getMainAliasManager().registerAlias("wookie", "true");
        final String line = "echo (wookie ewok)";
        final CommandFlow flux = commandManager.applyCommand(line);
        assertEquals("true ewok", flux.getContent());
    }

    @Test
    public final void testWithAliasStart() throws CommandException {
        ModuloAPI.getCommand().getMainAliasManager().registerAlias("wookie", "ewok");
        final String line = "echo (echo best wookie of the world)";
        final CommandFlow flux = commandManager.applyCommand(line);
        assertEquals("best ewok of the world", flux.getContent());
    }

}
