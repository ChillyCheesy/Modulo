package com.chillycheesy.hometracker.commands;

import com.chillycheesy.hometracker.ModuloAPI;
import com.chillycheesy.hometracker.commands.operator.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PowerOperationTest {

    private CommandManager commandManager;

    @BeforeEach
    public final void beforeEach() {
        final OperatorManager operatorManager = ModuloAPI.getCommand().getOperatorManager();
        operatorManager.registerItemToBuild(null,
                new ParenthesesOperator(),
                new PlusOperator(),
                new PowerOperator()
        );
        commandManager = ModuloAPI.getCommand().getCommandManager();
    }

    @Test
    public final void applyWithNoModulo() {
        final String line = "I Love 5 ewoks";
        final CommandFlux flux = commandManager.applyCommand(line);
        assertEquals(line, flux.getContent());
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "I Love 3 ^ 3 ewoks",
            "I Love 3 ^3 ewoks",
            "I Love 3^ 3 ewoks",
            "I Love 3^3 ewoks",
            "I Love 3   ^3 ewoks",
            "I Love 3 ^     3 ewoks",
            "I Love 3 ^3 ewoks",
            "I Love 3\n^\n3 ewoks",
            "I Love 3 \n^\n     3 ewoks",
            "I Love 3   \n^3 ewoks",
            "I Love 3   ^\n3 ewoks",
            "I Love 3^  \n  3 ewoks",
    })
    public final void applyWithSimpleModulo(String line) {
        final CommandFlux flux = commandManager.applyCommand(line);
        assertEquals("I Love 27.0 ewoks", flux.getContent());
    }

    @Test
    public final void applyWithDoubleModulo() {
        final String line = "I Love 5.5 ^ 2 ewoks";
        final CommandFlux flux = commandManager.applyCommand(line);
        assertEquals("I Love 30.25 ewoks", flux.getContent());
    }

    @Test
    public final void applyWithPriority() {
        final String line = "I Love 5 + 3 ^ 3 ewoks";
        final CommandFlux flux = commandManager.applyCommand(line);
        assertEquals("I Love 32.0 ewoks", flux.getContent());
    }

    @Test
    public final void applyWithParenthesesModulo() {
        final String line = "I Love 5 ^ (2 + 3) ewoks";
        final CommandFlux flux = commandManager.applyCommand(line);
        assertEquals("I Love 3125.0 ewoks", flux.getContent());
    }

}
