package com.chillycheesy.hometracker.commands.operators;

import com.chillycheesy.hometracker.ModuloAPI;
import com.chillycheesy.hometracker.commands.CommandFlow;
import com.chillycheesy.hometracker.commands.builder.CommandFlowBuilder;
import com.chillycheesy.hometracker.commands.operator.*;
import com.chillycheesy.hometracker.commands.operator.natif.ParenthesesOperator;
import com.chillycheesy.hometracker.commands.operator.natif.PlusOperator;
import com.chillycheesy.hometracker.commands.operator.natif.PowerOperator;
import com.chillycheesy.hometracker.utils.exception.CommandException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PowerOperationTest {

    private OperatorManager operatorManager;

    @BeforeEach
    public final void beforeEach() {
        operatorManager = ModuloAPI.getCommand().getOperatorManager();
        operatorManager.registerItemToBuild(null,
                new ParenthesesOperator(),
                new PlusOperator(),
                new PowerOperator()
        );
    }

    @AfterEach
    public final void afterEach() {
        ModuloAPI.getCommand().getOperatorManager().removeAllItems(null);
        ModuloAPI.getCommand().getCommandManager().removeAllItems(null);
    }

    @Test
    public final void applyWithNoModulo() throws CommandException {
        final String line = "I Love 5 ewoks";
        final CommandFlow flux = operatorManager.applyOperators(null, CommandFlowBuilder.create(line));
        assertEquals(line, flux.getContent());
    }

    @Test
    public final void applyWithSkipModulo() throws CommandException {
        final String line = "I Love \\^ 5 ewoks";
        final CommandFlow flux = operatorManager.applyOperators(null, CommandFlowBuilder.create(line));
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
    public final void applyWithSimpleModulo(String line) throws CommandException {
        final CommandFlow flux = operatorManager.applyOperators(null, CommandFlowBuilder.create(line));
        assertEquals("I Love 27.0 ewoks", flux.getContent());
    }

    @Test
    public final void applyWithDoubleModulo() throws CommandException {
        final String line = "I Love 5.5 ^ 2 ewoks";
        final CommandFlow flux = operatorManager.applyOperators(null, CommandFlowBuilder.create(line));
        assertEquals("I Love 30.25 ewoks", flux.getContent());
    }

    @Test
    public final void applyWithPriority() throws CommandException {
        final String line = "I Love 5 + 3 ^ 3 ewoks";
        final CommandFlow flux = operatorManager.applyOperators(null, CommandFlowBuilder.create(line));
        assertEquals("I Love 32.0 ewoks", flux.getContent());
    }

    @Test
    public final void applyWithParenthesesModulo() throws CommandException {
        final String line = "I Love 5 ^ (2 + 3) ewoks";
        final CommandFlow flux = operatorManager.applyOperators(null, CommandFlowBuilder.create(line));
        assertEquals("I Love 3125.0 ewoks", flux.getContent());
    }

}