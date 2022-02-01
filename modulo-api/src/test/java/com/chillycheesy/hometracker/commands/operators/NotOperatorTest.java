package com.chillycheesy.hometracker.commands.operators;

import com.chillycheesy.hometracker.ModuloAPI;
import com.chillycheesy.hometracker.commands.CommandFlux;
import com.chillycheesy.hometracker.commands.FluxBuilder;
import com.chillycheesy.hometracker.commands.operator.OperatorManager;
import com.chillycheesy.hometracker.commands.operator.natif.NotOperator;
import com.chillycheesy.hometracker.utils.exception.CommandException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class NotOperatorTest {

    private OperatorManager operatorManager;

    @BeforeEach
    public final void beforeEach() {
        operatorManager = ModuloAPI.getCommand().getOperatorManager();
        operatorManager.registerItemToBuild(null, new NotOperator());
    }

    @Test
    public final void applyWithNoNot() throws CommandException {
        final String line = "I Love true ewoks";
        final CommandFlux flux = operatorManager.applyOperators(null, FluxBuilder.create(line));
        assertEquals(line, flux.getContent());
    }

    @Test
    public final void applyWithSkipNot() throws CommandException {
        final String line = "I Love true \\! false ewoks";
        final CommandFlux flux = operatorManager.applyOperators(null, FluxBuilder.create(line));
        assertEquals(line, flux.getContent());
    }

    @ParameterizedTest
    @ValueSource(strings = {
        "I Love ! true ewoks",
        "I Love !true ewoks",
        "I Love! true ewoks",
        "I Love!true ewoks",
        "I Love  !true ewoks",
        "I Love!     true ewoks",
        "I Love!true ewoks",
        "I Love\n!\ntrue ewoks",
        "I Love \n!\n     true ewoks",
        "I Love   \n!true ewoks",
        "I Love  !\ntrue ewoks",
        "I Love!  \n  true ewoks",
    })
    public final void applyWithSimpleNot(String line) throws CommandException {
        final CommandFlux flux = operatorManager.applyOperators(null, FluxBuilder.create(line));
        assertEquals("I Love false ewoks", flux.getContent());
    }

    @Test
    public final void applyWithMultipuleNot() throws CommandException {
        final String line = "I Love !!!!!false ewoks";
        final CommandFlux flux = operatorManager.applyOperators(null, FluxBuilder.create(line));
        assertEquals("I Love true ewoks", flux.getContent());
    }
}
