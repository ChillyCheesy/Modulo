package com.chillycheesy.hometracker.commands.operators;

import com.chillycheesy.hometracker.ModuloAPI;
import com.chillycheesy.hometracker.commands.CommandFlux;
import com.chillycheesy.hometracker.commands.FluxBuilder;
import com.chillycheesy.hometracker.commands.operator.OperatorManager;
import com.chillycheesy.hometracker.commands.operator.natif.GreaterOrEqualsThanOperator;
import com.chillycheesy.hometracker.utils.exception.CommandException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GreaterOrEqualsThanOperatorTest {

    private OperatorManager operatorManager;

    @BeforeEach
    public final void beforeEach() {
        operatorManager = ModuloAPI.getCommand().getOperatorManager();
        operatorManager.registerItemToBuild(null, new GreaterOrEqualsThanOperator());
    }

    @AfterEach
    public final void afterEach() {
        ModuloAPI.getCommand().getOperatorManager().removeAllItems(null);
        ModuloAPI.getCommand().getCommandManager().removeAllItems(null);
    }

    @Test
    public final void applyWithNoGreaterOrEqualsThan() throws CommandException {
        final String line = "I Love 6 ewoks";
        final CommandFlux flux = operatorManager.applyOperators(null, FluxBuilder.create(line));
        assertEquals(line, flux.getContent());
    }

    @Test
    public final void applyWithSkipGreaterOrEqualsThan() throws CommandException {
        final String line = "I Love 6 \\>= 10 ewoks";
        final CommandFlux flux = operatorManager.applyOperators(null, FluxBuilder.create(line));
        assertEquals(line, flux.getContent());
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "I Love 36 >= 3 ewoks",
            "I Love 36 >=3 ewoks",
            "I Love 36>= 3 ewoks",
            "I Love 36>=3 ewoks",
            "I Love 36   >=3 ewoks",
            "I Love 36 >=     3 ewoks",
            "I Love 36 >=3 ewoks",
            "I Love 36\n>=\n3 ewoks",
            "I Love 36 \n>=\n     3 ewoks",
            "I Love 36   \n>=3 ewoks",
            "I Love 36   >=\n3 ewoks",
            "I Love 36>=  \n  3 ewoks",
            "I Love 3 >= 3 ewoks",
            "I Love 3 >=3 ewoks",
            "I Love 3>= 3 ewoks",
            "I Love 3>=3 ewoks",
            "I Love 3   >=3 ewoks",
            "I Love 3 >=     3 ewoks",
            "I Love 3 >=3 ewoks",
            "I Love 3\n>=\n3 ewoks",
            "I Love 3 \n>=\n     3 ewoks",
            "I Love 3   \n>=3 ewoks",
            "I Love 3   >=\n3 ewoks",
            "I Love 3>=  \n  3 ewoks"
    })
    public final void applyWithSimpleGreaterOrEqualsThan(String line) throws CommandException {
        final CommandFlux flux = operatorManager.applyOperators(null, FluxBuilder.create(line));
        assertEquals("I Love true ewoks", flux.getContent());
    }

    @Test
    public final void applyWithNotGreaterOrEqualsThan() throws CommandException {
        final String line = "I Love 1 >= 10 ewoks";
        final CommandFlux flux = operatorManager.applyOperators(null, FluxBuilder.create(line));
        assertEquals("I Love false ewoks",flux.getContent());
    }
}
