package com.chillycheesy.modulo.commands.operators;

import com.chillycheesy.modulo.ModuloAPI;
import com.chillycheesy.modulo.commands.CommandFlow;
import com.chillycheesy.modulo.commands.builder.CommandFlowBuilder;
import com.chillycheesy.modulo.commands.operator.OperatorManager;
import com.chillycheesy.modulo.commands.operator.natif.EqualsOperator;
import com.chillycheesy.modulo.utils.exception.CommandException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class EqualsOperatorTest {

    private OperatorManager operatorManager;

    @BeforeEach
    public final void beforeEach() {
        operatorManager = ModuloAPI.getCommand().getOperatorManager();
        operatorManager.registerItemToBuild(null, new EqualsOperator());
    }

    @AfterEach
    public final void afterEach() {
        ModuloAPI.getCommand().getOperatorManager().removeAllItems(null);
    }

    @Test
    public final void applyWithNoEquals() throws CommandException {
        final String line = "I Love true ewoks";
        final CommandFlow flux = operatorManager.applyOperators(null, CommandFlowBuilder.create(line));
        assertEquals(line, flux.getContent());
    }

    @Test
    public final void applyWithSkipEquals() throws CommandException {
        final String line = "I Love true \\== false ewoks";
        final CommandFlow flux = operatorManager.applyOperators(null, CommandFlowBuilder.create(line));
        assertEquals(line, flux.getContent());
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "I Love true== true ewoks",
            "I Love true==true ewoks",
            "I Love ewok == ewok ewoks",
            "I Love 5==5 ewoks",
            "I Love ewok   ==ewok ewoks",
            "I Love 13ddtrue == 13ddtrue ewoks",
            "I Love true==true ewoks",
            "I Love true\n==\ntrue ewoks",
            "I Love true \n==\n     true ewoks",
            "I Love true   \n==true ewoks",
            "I Love true  ==\ntrue ewoks",
            "I Love true==  \n  true ewoks",
    })
    public final void applyWithSimpleEquals(String line) throws CommandException {
        final CommandFlow flux = operatorManager.applyOperators(null, CommandFlowBuilder.create(line));
        assertEquals("I Love true ewoks", flux.getContent());
    }


    @ParameterizedTest
    @ValueSource(strings = {
            "I Love true == false ewoks",
            "I Love false == true ewoks",
            "I Love 2 == 1 ewoks",
            "I Love r4t4t4r4r433 == 34rt534t3tg ewoks"
    })
    public final void applyWithFalseEquals(String line) throws CommandException {
        final CommandFlow flux = operatorManager.applyOperators(null, CommandFlowBuilder.create(line));
        assertEquals("I Love false ewoks", flux.getContent());
    }
}
