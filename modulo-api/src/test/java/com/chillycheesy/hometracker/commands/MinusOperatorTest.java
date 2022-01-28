package com.chillycheesy.hometracker.commands;

import com.chillycheesy.hometracker.ModuloAPI;
import com.chillycheesy.hometracker.commands.operator.MinusOperator;
import com.chillycheesy.hometracker.commands.operator.OperatorManager;
import com.chillycheesy.hometracker.commands.operator.ParenthesesOperator;
import com.chillycheesy.hometracker.commands.operator.PlusOperator;
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

    @Test
    public final void applyWithNoMinus() {
        final String line = "I Love 5 ewoks";
        final CommandFlux flux = operatorManager.applyOperators(null, FluxBuilder.create(line));
        assertEquals(line, flux.getContent());
    }

    @Test
    public final void applyWithSkipMinus() {
        final String line = "I Love 5 \\- 5 ewoks";
        final CommandFlux flux = operatorManager.applyOperators(null, FluxBuilder.create(line));
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
    public final void applyWithSimpleMinus(String line) {
        final CommandFlux flux = operatorManager.applyOperators(null, FluxBuilder.create(line));
        assertEquals("I Love 6.0 ewoks", flux.getContent());
    }

    @Test
    public final void applyWithDoubleMinus() {
        final String line = "I Love 5.5 - 1.1 ewoks";
        final CommandFlux flux = operatorManager.applyOperators(null, FluxBuilder.create(line));
        assertEquals("I Love 4.4 ewoks", flux.getContent());
    }

    @Test
    public final void applyWithTripleOperation() {
        final String line = "I Love 5.5 + 1.1 - 16 ewoks";
        final CommandFlux flux = operatorManager.applyOperators(null, FluxBuilder.create(line));
        assertEquals("I Love -9.4 ewoks", flux.getContent());
    }

    @Test
    public final void applyWithNegativeMinus() {
        final String line = "I Love 5 - -5 ewoks";
        final CommandFlux flux = operatorManager.applyOperators(null, FluxBuilder.create(line));
        assertEquals("I Love 10.0 ewoks", flux.getContent());
    }

    @Test
    public final void applyWithNegativeMinus2() {
        final String line = "I Love -5 - -5 ewoks";
        final CommandFlux flux = operatorManager.applyOperators(null, FluxBuilder.create(line));
        assertEquals("I Love 0.0 ewoks", flux.getContent());
    }

    @Test
    public final void applyWithParenthesesAddition() {
        final String line = "I Love (5) - (1 + 4) ewoks";
        final CommandFlux flux = operatorManager.applyOperators(null, FluxBuilder.create(line));
        assertEquals("I Love 0.0 ewoks", flux.getContent());
    }

}
