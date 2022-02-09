package com.chillycheesy.modulo.commands.operators;

import com.chillycheesy.modulo.ModuloAPI;
import com.chillycheesy.modulo.commands.CommandFlow;
import com.chillycheesy.modulo.commands.builder.CommandFlowBuilder;
import com.chillycheesy.modulo.commands.operator.OperatorManager;
import com.chillycheesy.modulo.commands.operator.natif.ParenthesesOperator;
import com.chillycheesy.modulo.commands.operator.natif.XorOperator;
import com.chillycheesy.modulo.utils.exception.CommandException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class XorOperatorTest {

    private OperatorManager operatorManager;

    @BeforeEach
    public final void beforeEach() {
        operatorManager = ModuloAPI.getCommand().getOperatorManager();
        operatorManager.registerItemToBuild(null, new XorOperator(), new ParenthesesOperator());
    }

    @AfterEach
    public final void afterEach() {
        ModuloAPI.getCommand().getOperatorManager().removeAllItems(null);
        ModuloAPI.getCommand().getCommandManager().removeAllItems(null);
    }

    @Test
    public final void applyWithNoXor() throws CommandException {
        final String line = "I Love true ewoks";
        final CommandFlow flux = operatorManager.applyOperators(null, CommandFlowBuilder.create(line));
        assertEquals(line, flux.getContent());
    }

    @Test
    public final void applyWithSkipXor() throws CommandException {
        final String line = "I Love true \\|& false ewoks";
        final CommandFlow flux = operatorManager.applyOperators(null, CommandFlowBuilder.create(line));
        assertEquals(line, flux.getContent());
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "I Love true&| true ewoks",
            "I Love true&|true ewoks",
            "I Love true&| true ewoks",
            "I Love true&|true ewoks",
            "I Love true  &|true ewoks",
            "I Love true&|     true ewoks",
            "I Love true&|true ewoks",
            "I Love true\n&|\ntrue ewoks",
            "I Love true \n&|\n     true ewoks",
            "I Love true   \n&|true ewoks",
            "I Love true  &|\ntrue ewoks",
            "I Love true&|  \n  true ewoks",
            "I Love true|& true ewoks",
            "I Love true|&true ewoks",
            "I Love true|& true ewoks",
            "I Love true|&true ewoks",
            "I Love true  |&true ewoks",
            "I Love true|&     true ewoks",
            "I Love true|&true ewoks",
            "I Love true\n|&\ntrue ewoks",
            "I Love true \n|&\n     true ewoks",
            "I Love true   \n|&true ewoks",
            "I Love true  |&\ntrue ewoks",
            "I Love true|&  \n  true ewoks"
    })
    public final void applyWithSimpleXor(String line) throws CommandException {
        final CommandFlow flux = operatorManager.applyOperators(null, CommandFlowBuilder.create(line));
        assertEquals("I Love false ewoks", flux.getContent());
    }

    @Test
    public final void applyWithParenthesesXor() throws CommandException {
        final String line = "I Love (true) |& (true |& false) ewoks";
        final CommandFlow flux = operatorManager.applyOperators(null, CommandFlowBuilder.create(line));
        assertEquals("I Love false ewoks", flux.getContent());
    }

    @Test
    public final void applyWithMultipuleXor() throws CommandException {
        final String line = "I Love true |& true &| false ewoks";
        final CommandFlow flux = operatorManager.applyOperators(null, CommandFlowBuilder.create(line));
        assertEquals("I Love false ewoks", flux.getContent());
    }


    @ParameterizedTest
    @ValueSource(strings = {
            "I Love true |& false ewoks",
            "I Love false |& true ewoks"
    })
    public final void applyWithDifferentXor(String line) throws CommandException {
       final CommandFlow flux = operatorManager.applyOperators(null, CommandFlowBuilder.create(line));
       assertEquals("I Love true ewoks", flux.getContent());
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "I Love true |& true ewoks",
            "I Love false |& false ewoks"
    })
    public final void applyWithSameXor(String line) throws CommandException {
        final CommandFlow flux = operatorManager.applyOperators(null, CommandFlowBuilder.create(line));
        assertEquals("I Love false ewoks", flux.getContent());
    }
}
