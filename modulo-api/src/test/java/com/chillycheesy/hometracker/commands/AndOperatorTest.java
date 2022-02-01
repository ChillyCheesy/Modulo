package com.chillycheesy.hometracker.commands;

import com.chillycheesy.hometracker.ModuloAPI;
import com.chillycheesy.hometracker.commands.operator.OperatorManager;
import com.chillycheesy.hometracker.commands.operator.natif.AndOperator;
import com.chillycheesy.hometracker.utils.exception.CommandException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AndOperatorTest {

    private OperatorManager operatorManager;

    @BeforeEach
    public final void beforeEach() {
        operatorManager = ModuloAPI.getCommand().getOperatorManager();
        operatorManager.registerItemToBuild(null, new AndOperator());
    }

    @Test
    public final void applyWithNoAnd() throws CommandException {
        final String line = "I Love true ewoks";
        final CommandFlux flux = operatorManager.applyOperators(null, FluxBuilder.create(line));
        assertEquals(line, flux.getContent());
    }

    @Test
    public final void applyWithSkipAnd() throws CommandException {
        final String line = "I Love true \\&& false ewoks";
        final CommandFlux flux = operatorManager.applyOperators(null, FluxBuilder.create(line));
        assertEquals(line, flux.getContent());
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "I Love true && true ewoks",
            "I Love true &&true ewoks",
            "I Love true&& true ewoks",
            "I Love true&&true ewoks",
            "I Love true   &&true ewoks",
            "I Love true &&     true ewoks",
            "I Love true &&true ewoks",
            "I Love true\n&&\ntrue ewoks",
            "I Love true \n&&\n     true ewoks",
            "I Love true   \n&&true ewoks",
            "I Love true   &&\ntrue ewoks",
            "I Love true&&  \n  true ewoks",
    })
    public final void applyWithSimpleAnd(String line) throws CommandException {
        final CommandFlux flux = operatorManager.applyOperators(null, FluxBuilder.create(line));
        assertEquals("I Love true ewoks", flux.getContent());
    }

    @Test
    public final void applyWithParenthesesAnd() throws CommandException {
        final String line = "I Love true && true && false ewoks";
        final CommandFlux flux = operatorManager.applyOperators(null, FluxBuilder.create(line));
        assertEquals("I Love false ewoks", flux.getContent());
    }
}
