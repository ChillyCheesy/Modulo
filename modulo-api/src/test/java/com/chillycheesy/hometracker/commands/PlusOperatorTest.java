package com.chillycheesy.hometracker.commands;

import com.chillycheesy.hometracker.ModuloAPI;
import com.chillycheesy.hometracker.commands.operator.OperatorManager;
import com.chillycheesy.hometracker.commands.operator.ParenthesesOperator;
import com.chillycheesy.hometracker.commands.operator.PlusOperator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PlusOperatorTest {

    private CommandManager commandManager;

    @BeforeEach
    public final void beforeEach() {
        final OperatorManager operatorManager = ModuloAPI.getCommand().getOperatorManager();
        commandManager = ModuloAPI.getCommand().getCommandManager();
        operatorManager.registerItemToBuild(null, new ParenthesesOperator(), new PlusOperator());
    }

    @Test
    public final void applyWithNoAddition() {
        final String line = "I Love 5 ewoks";
        final CommandFlux flux = commandManager.applyCommand(line);
        assertEquals(line, flux.getContent());
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "I Love 3 + 3 ewoks",
            "I Love 3 +3 ewoks",
            "I Love 3+ 3 ewoks",
            "I Love 3+3 ewoks",
            "I Love 3   +3 ewoks",
            "I Love 3 +     3 ewoks",
            "I Love 3 +3 ewoks",
            "I Love 3\n+\n3 ewoks",
            "I Love 3 \n+\n     3 ewoks",
            "I Love 3   \n+3 ewoks",
            "I Love 3   +\n3 ewoks",
            "I Love 3+  \n  3 ewoks",
    })
    public final void applyWithSimpleAddition(String line) {
        final CommandFlux flux = commandManager.applyCommand(line);
        assertEquals("I Love 6.0 ewoks", flux.getContent());
    }

    @Test
    public final void applyWithDoubleAddition() {
        final String line = "I Love 5.5 + 1.1 ewoks";
        final CommandFlux flux = commandManager.applyCommand(line);
        assertEquals("I Love 6.6 ewoks", flux.getContent());
    }

    @Test
    public final void applyWithTripleAddition() {
        final String line = "I Love 5.5 + 1.1 + 24 ewoks";
        final CommandFlux flux = commandManager.applyCommand(line);
        assertEquals("I Love 30.6 ewoks", flux.getContent());
    }

    @Test
    public final void applyWithNegativeAddition() {
        final String line = "I Love -5.5 + 1.1 + 24 ewoks";
        final CommandFlux flux = commandManager.applyCommand(line);
        assertEquals("I Love 19.6 ewoks", flux.getContent());
    }

    @Test
    public final void applyWithParenthesesAddition() {
        final String line = "I Love (5) + (1 + 4) ewoks";
        final CommandFlux flux = commandManager.applyCommand(line);
        assertEquals("I Love 10.0 ewoks", flux.getContent());
    }

}
