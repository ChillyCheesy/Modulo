package com.chillycheesy.hometracker.commands;

import com.chillycheesy.hometracker.ModuloAPI;
import com.chillycheesy.hometracker.commands.operator.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ModuloOperationTest {

    private OperatorManager operatorManager;

    @BeforeEach
    public final void beforeEach() {
        operatorManager = ModuloAPI.getCommand().getOperatorManager();
        operatorManager.registerItemToBuild(null,
                new ParenthesesOperator(),
                new PlusOperator(),
                new MinusOperator(),
                new MultiplicationOperator(),
                new DivideOperator(),
                new ModuloOperation()
        );
    }

    @Test
    public final void applyWithNoModulo() {
        final String line = "I Love 5 ewoks";
        final CommandFlux flux = operatorManager.applyOperators(null, FluxBuilder.create(line));
        assertEquals(line, flux.getContent());
    }

    @Test
    public final void applyWithSkipModulo() {
        final String line = "I Love \\% 5 ewoks";
        final CommandFlux flux = operatorManager.applyOperators(null, FluxBuilder.create(line));
        assertEquals(line, flux.getContent());
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "I Love 3 % 3 ewoks",
            "I Love 3 %3 ewoks",
            "I Love 3% 3 ewoks",
            "I Love 3%3 ewoks",
            "I Love 3   %3 ewoks",
            "I Love 3 %     3 ewoks",
            "I Love 3 %3 ewoks",
            "I Love 3\n%\n3 ewoks",
            "I Love 3 \n%\n     3 ewoks",
            "I Love 3   \n%3 ewoks",
            "I Love 3   %\n3 ewoks",
            "I Love 3%  \n  3 ewoks",
    })
    public final void applyWithSimpleModulo(String line) {
        final CommandFlux flux = operatorManager.applyOperators(null, FluxBuilder.create(line));
        assertEquals("I Love 0.0 ewoks", flux.getContent());
    }

    @Test
    public final void applyWithDoubleModulo() {
        final String line = "I Love 5.5 % 1.1 ewoks";
        final CommandFlux flux = operatorManager.applyOperators(null, FluxBuilder.create(line));
        assertEquals("I Love 1.0999999999999996 ewoks", flux.getContent());
    }

    @Test
    public final void applyWithPriority() {
        final String line = "I Love 5 + 3 % 3 ewoks";
        final CommandFlux flux = operatorManager.applyOperators(null, FluxBuilder.create(line));
        assertEquals("I Love 5.0 ewoks", flux.getContent());
    }

    @Test
    public final void applyWithParenthesesModulo() {
        final String line = "I Love 5 % (2 + 3) ewoks";
        final CommandFlux flux = operatorManager.applyOperators(null, FluxBuilder.create(line));
        assertEquals("I Love 0.0 ewoks", flux.getContent());
    }

}
