package com.chillycheesy.hometracker.commands.operators;

import com.chillycheesy.hometracker.ModuloAPI;
import com.chillycheesy.hometracker.commands.CommandFlow;
import com.chillycheesy.hometracker.commands.builder.CommandFlowBuilder;
import com.chillycheesy.hometracker.commands.operator.natif.MinusOperator;
import com.chillycheesy.hometracker.commands.operator.OperatorManager;
import com.chillycheesy.hometracker.commands.operator.natif.ParenthesesOperator;
import com.chillycheesy.hometracker.commands.operator.natif.PlusOperator;
import com.chillycheesy.hometracker.utils.exception.CommandException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MinusOperatorTest {

    private OperatorManager operatorManager;

    @BeforeEach
    public final void beforeEach() {
        operatorManager = ModuloAPI.getCommand().getOperatorManager();
        operatorManager.registerItemToBuild(null, new ParenthesesOperator(), new PlusOperator(), new MinusOperator());
    }

    @AfterEach
    public final void afterEach() {
        ModuloAPI.getCommand().getOperatorManager().removeAllItems(null);
        ModuloAPI.getCommand().getCommandManager().removeAllItems(null);
    }

    @Test
    public final void applyWithNoMinus() throws CommandException {
        final String line = "I Love 5 ewoks";
        final CommandFlow flux = operatorManager.applyOperators(null, CommandFlowBuilder.create(line));
        assertEquals(line, flux.getContent());
    }

    @Test
    public final void applyWithSkipMinus() throws CommandException {
        final String line = "I Love 5 \\- 5 ewoks";
        final CommandFlow flux = operatorManager.applyOperators(null, CommandFlowBuilder.create(line));
        assertEquals(line, flux.getContent());
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "I Love 9 - 3 ewoks",
            "I Love 9 -3 ewoks",
            "I Love 9- 3 ewoks",
            "I Love 9-3 ewoks",
            "I Love 9   -3 ewoks",
            "I Love 9 -     3 ewoks",
            "I Love 9 -3 ewoks",
            "I Love 9\n-\n3 ewoks",
            "I Love 9 \n-\n     3 ewoks",
            "I Love 15   \n-9 ewoks",
            "I Love 15   -\n9 ewoks",
            "I Love 15-  \n  9 ewoks",
    })
    public final void applyWithSimpleMinus(String line) throws CommandException {
        final CommandFlow flux = operatorManager.applyOperators(null, CommandFlowBuilder.create(line));
        assertEquals("I Love 6.0 ewoks", flux.getContent());
    }

    @Test
    public final void applyWithDoubleMinus() throws CommandException {
        final String line = "I Love 5.5 - 1.1 ewoks";
        final CommandFlow flux = operatorManager.applyOperators(null, CommandFlowBuilder.create(line));
        assertEquals("I Love 4.4 ewoks", flux.getContent());
    }

    @Test
    public final void applyWithTripleOperation() throws CommandException {
        final String line = "I Love 5.5 + 1.1 - 16 ewoks";
        final CommandFlow flux = operatorManager.applyOperators(null, CommandFlowBuilder.create(line));
        assertEquals("I Love -9.4 ewoks", flux.getContent());
    }

    @Test
    public final void applyWithNegativeMinus() throws CommandException {
        final String line = "I Love 5 - -5 ewoks";
        final CommandFlow flux = operatorManager.applyOperators(null, CommandFlowBuilder.create(line));
        assertEquals("I Love 10.0 ewoks", flux.getContent());
    }

    @Test
    public final void applyWithNegativeMinus2() throws CommandException {
        final String line = "I Love -5 - -5 ewoks";
        final CommandFlow flux = operatorManager.applyOperators(null, CommandFlowBuilder.create(line));
        assertEquals("I Love 0.0 ewoks", flux.getContent());
    }

    @Test
    public final void applyWithParenthesesAddition() throws CommandException {
        final String line = "I Love (5) - (1 + 4) ewoks";
        final CommandFlow flux = operatorManager.applyOperators(null, CommandFlowBuilder.create(line));
        assertEquals("I Love 0.0 ewoks", flux.getContent());
    }

}
