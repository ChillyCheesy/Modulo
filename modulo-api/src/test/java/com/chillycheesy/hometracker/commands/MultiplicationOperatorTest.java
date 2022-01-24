package com.chillycheesy.hometracker.commands;

import com.chillycheesy.hometracker.ModuloAPI;
import com.chillycheesy.hometracker.commands.operator.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MultiplicationOperatorTest {

    private CommandManager commandManager;

    @BeforeEach
    public final void beforeEach() {
        final OperatorManager operatorManager = ModuloAPI.getCommand().getOperatorManager();
        operatorManager.registerItemToBuild(null,
                new ParenthesesOperator(),
                new PlusOperator(),
                new MinusOperator(),
                new MultiplicationOperator()
        );
        commandManager = ModuloAPI.getCommand().getCommandManager();
    }

    @Test
    public final void applyWithNoMultiplication() {
        final String line = "I Love 5 ewoks";
        final CommandFlux flux = commandManager.applyCommand(line);
        assertEquals(line, flux.getContent());
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "I Love 3 * 3 ewoks",
            "I Love 3 *3 ewoks",
            "I Love 3* 3 ewoks",
            "I Love 3*3 ewoks",
            "I Love 3   *3 ewoks",
            "I Love 3 *     3 ewoks",
            "I Love 3 *3 ewoks",
            "I Love 3\n*\n3 ewoks",
            "I Love 3 \n*\n     3 ewoks",
            "I Love 3   \n*3 ewoks",
            "I Love 3   *\n3 ewoks",
            "I Love 3*  \n  3 ewoks",
    })
    public final void applyWithSimpleMultiplication(String line) {
        final CommandFlux flux = commandManager.applyCommand(line);
        assertEquals("I Love 9.0 ewoks", flux.getContent());
    }

    @Test
    public final void applyWithDoubleMultiplication() {
        final String line = "I Love 5.5 * 1.1 ewoks";
        final CommandFlux flux = commandManager.applyCommand(line);
        assertEquals("I Love 6.050000000000001 ewoks", flux.getContent());
    }

    @Test
    public final void applyWithPriority() {
        final String line = "I Love 5 + 2 * 3 ewoks";
        final CommandFlux flux = commandManager.applyCommand(line);
        assertEquals("I Love 11.0 ewoks", flux.getContent());
    }

    @Test
    public final void applyWithParenthesesMultiplication() {
        final String line = "I Love 5 * (2 + 3) ewoks";
        final CommandFlux flux = commandManager.applyCommand(line);
        assertEquals("I Love 25.0 ewoks", flux.getContent());
    }

    @Test
    public final void applyWithNegativeMultiplication() {
        final String line = "I Love -1 * 10 ewoks";
        final CommandFlux flux = commandManager.applyCommand(line);
        assertEquals("I Love -10.0 ewoks", flux.getContent());
    }

    @Test
    public final void applyWithNegativeMultiplication2() {
        final String line = "I Love -5 * -5 ewoks";
        final CommandFlux flux = commandManager.applyCommand(line);
        assertEquals("I Love 25.0 ewoks", flux.getContent());
    }

}
