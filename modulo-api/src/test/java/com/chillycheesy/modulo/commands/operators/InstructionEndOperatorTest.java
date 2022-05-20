package com.chillycheesy.modulo.commands.operators;

import com.chillycheesy.modulo.ModuloAPI;
import com.chillycheesy.modulo.commands.CommandFlow;
import com.chillycheesy.modulo.commands.CommandManager;
import com.chillycheesy.modulo.commands.natif.EchoCommand;
import com.chillycheesy.modulo.commands.operator.natif.InstructionEndOperator;
import com.chillycheesy.modulo.commands.operator.natif.ParenthesesOperator;
import com.chillycheesy.modulo.utils.exception.CommandException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class InstructionEndOperatorTest {

    private CommandManager commandManager;

    @BeforeEach
    public final void beforeEach() {
        commandManager = ModuloAPI.getCommand().getCommandManager();
        ModuloAPI.getCommand().getOperatorManager().registerItemToBuild(null,  new ParenthesesOperator(), new InstructionEndOperator());
        commandManager.registerItemToBuild(null, new EchoCommand());
    }

    @AfterEach
    public final void afterEach() {
        ModuloAPI.getCommand().getOperatorManager().removeAllItems(null);
        ModuloAPI.getCommand().getCommandManager().removeAllItems(null);
    }

    @Test
    public final void testWithNoEndParentheses() throws CommandException {
        final String line = "echo I (echo Love ewoks)";
        final CommandFlow flux = commandManager.applyCommand(line);
        assertEquals("I Love ewoks", flux.getContent());
    }

    @Test
    public final void testWithParentheses() throws CommandException {
        final String line = "echo I Love (echo ewoks; echo wookies)";
        final CommandFlow flux = commandManager.applyCommand(line);
        assertEquals("I Love wookies", flux.getContent());
    }

    @Test
    public final void testWithBracket() throws CommandException {
        final String line = "echo I Love [echo ewoks; echo wookies]";
        final CommandFlow flux = commandManager.applyCommand(line);
        assertEquals("I Love [echo ewoks; echo wookies]", flux.getContent());
    }

    @Test
    public final void testWithBrace() throws CommandException {
        final String line = "echo I Love {echo ewoks; echo wookies}";
        final CommandFlow flux = commandManager.applyCommand(line);
        assertEquals("I Love {echo ewoks; echo wookies}", flux.getContent());
    }

    @Test
    public final void testMultiple() throws CommandException {
        final String line = "echo I Love (echo ewoks; echo wookies); echo ee chee wa maa";
        final CommandFlow flux = commandManager.applyCommand(line);
        assertEquals("ee chee wa maa", flux.getContent());
    }

}
