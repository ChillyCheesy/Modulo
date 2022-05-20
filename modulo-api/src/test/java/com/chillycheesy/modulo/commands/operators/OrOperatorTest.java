package com.chillycheesy.modulo.commands.operators;

import com.chillycheesy.modulo.ModuloAPI;
import com.chillycheesy.modulo.commands.CommandFlow;
import com.chillycheesy.modulo.commands.builder.CommandFlowBuilder;
import com.chillycheesy.modulo.commands.operator.OperatorManager;
import com.chillycheesy.modulo.commands.operator.natif.OrOperator;
import com.chillycheesy.modulo.commands.operator.natif.ParenthesesOperator;
import com.chillycheesy.modulo.utils.exception.CommandException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class OrOperatorTest {

    private OperatorManager operatorManager;

    @BeforeEach
    public final void beforeEach() {
        operatorManager = ModuloAPI.getCommand().getOperatorManager();
        operatorManager.registerItemToBuild(null, new OrOperator(), new ParenthesesOperator());
    }

    @AfterEach
    public final void afterEach() {
        ModuloAPI.getCommand().getOperatorManager().removeAllItems(null);
        ModuloAPI.getCommand().getCommandManager().removeAllItems(null);
    }

    @Test
    public final void applyWithNoOr() throws CommandException {
        final String line = "I Love true ewoks";
        final CommandFlow flux = operatorManager.applyOperators(null, CommandFlowBuilder.create(line));
        assertEquals(line, flux.getContent());
    }

    @Test
    public final void applyWithSkipOr() throws CommandException {
        final String line = "I Love true \\|| false ewoks";
        final CommandFlow flux = operatorManager.applyOperators(null, CommandFlowBuilder.create(line));
        assertEquals(line, flux.getContent());
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "I Love true|| true ewoks",
            "I Love true||true ewoks",
            "I Love true|| true ewoks",
            "I Love true||true ewoks",
            "I Love true  ||true ewoks",
            "I Love true||     true ewoks",
            "I Love true||true ewoks",
            "I Love true\n||\ntrue ewoks",
            "I Love true \n||\n     true ewoks",
            "I Love true   \n||true ewoks",
            "I Love true  ||\ntrue ewoks",
            "I Love true||  \n  true ewoks",
    })
    public final void applyWithSimpleOr(String line) throws CommandException {
        final CommandFlow flux = operatorManager.applyOperators(null, CommandFlowBuilder.create(line));
        assertEquals("I Love true ewoks", flux.getContent());
    }

    @Test
    public final void applyWithParenthesesOr() throws CommandException {
        final String line = "I Love (true) || (true || false) ewoks";
        final CommandFlow flux = operatorManager.applyOperators(null, CommandFlowBuilder.create(line));
        assertEquals("I Love true ewoks", flux.getContent());
    }

    @Test
    public final void applyWithMultipuleOr() throws CommandException {
        final String line = "I Love true || true || false ewoks";
        final CommandFlow flux = operatorManager.applyOperators(null, CommandFlowBuilder.create(line));
        assertEquals("I Love true ewoks", flux.getContent());
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "I Love true || false ewoks",
            "I Love false || true ewoks"
    })
    public final void applyWithDifferentOr(String line) throws CommandException {
        final CommandFlow flux = operatorManager.applyOperators(null, CommandFlowBuilder.create(line));
        assertEquals("I Love true ewoks", flux.getContent());
    }

    @Test
    public final void applyWithSameOr() throws CommandException {
        String line = "I Love true || true ewoks";
        CommandFlow flux = operatorManager.applyOperators(null, CommandFlowBuilder.create(line));
        assertEquals("I Love true ewoks", flux.getContent());
        line = "I Love false || false ewoks";
        flux = operatorManager.applyOperators(null, CommandFlowBuilder.create(line));
        assertEquals("I Love false ewoks", flux.getContent());
    }
}
