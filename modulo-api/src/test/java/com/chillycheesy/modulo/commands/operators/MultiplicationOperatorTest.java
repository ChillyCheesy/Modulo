package com.chillycheesy.modulo.commands.operators;

import com.chillycheesy.modulo.ModuloAPI;
import com.chillycheesy.modulo.commands.CommandFlow;
import com.chillycheesy.modulo.commands.builder.CommandFlowBuilder;
import com.chillycheesy.modulo.commands.operator.OperatorManager;
import com.chillycheesy.modulo.commands.operator.natif.MinusOperator;
import com.chillycheesy.modulo.commands.operator.natif.MultiplicationOperator;
import com.chillycheesy.modulo.commands.operator.natif.ParenthesesOperator;
import com.chillycheesy.modulo.commands.operator.natif.PlusOperator;
import com.chillycheesy.modulo.utils.exception.CommandException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MultiplicationOperatorTest {

    private OperatorManager operatorManager;

    @BeforeEach
    public final void beforeEach() {
        operatorManager = ModuloAPI.getCommand().getOperatorManager();
        operatorManager.registerItemToBuild(null,
                new ParenthesesOperator(),
                new PlusOperator(),
                new MinusOperator(),
                new MultiplicationOperator()
        );
    }

    @AfterEach
    public final void afterEach() {
        ModuloAPI.getCommand().getOperatorManager().removeAllItems(null);
        ModuloAPI.getCommand().getCommandManager().removeAllItems(null);
    }

    @Test
    public final void applyWithNoMultiplication() throws CommandException {
        final String line = "I Love 5 ewoks";
        final CommandFlow flux = operatorManager.applyOperators(null, CommandFlowBuilder.create(line));
        assertEquals(line, flux.getContent());
    }

    @Test
    public final void applyWithSkipMultiplication() throws CommandException {
        final String line = "I Love \\* 5 ewoks";
        final CommandFlow flux = operatorManager.applyOperators(null, CommandFlowBuilder.create(line));
        assertEquals(line, flux.getContent());
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "I Love 3 * 3 ewoks",
            "I Love 3 *3 ewoks",
            "I Love 3* 3 ewoks",
            "I Love 3*3 ewoks",
            "I Love 3   *3 ewoks",
            "I Love 3 *     3 ewoks",
            "I Love 3 *3 ewoks",
            "I Love 3\n*\n3 ewoks",
            "I Love 3 \n*\n     3 ewoks",
            "I Love 3   \n*3 ewoks",
            "I Love 3   *\n3 ewoks",
            "I Love 3*  \n  3 ewoks",
    })
    public final void applyWithSimpleMultiplication(String line) throws CommandException {
        final CommandFlow flux = operatorManager.applyOperators(null, CommandFlowBuilder.create(line));
        assertEquals("I Love 9.0 ewoks", flux.getContent());
    }

    @Test
    public final void applyWithDoubleMultiplication() throws CommandException {
        final String line = "I Love 5.5 * 1.1 ewoks";
        final CommandFlow flux = operatorManager.applyOperators(null, CommandFlowBuilder.create(line));
        assertEquals("I Love 6.050000000000001 ewoks", flux.getContent());
    }

    @Test
    public final void applyWithPriority() throws CommandException {
        final String line = "I Love 5 + 2 * 3 ewoks";
        final CommandFlow flux = operatorManager.applyOperators(null, CommandFlowBuilder.create(line));
        assertEquals("I Love 11.0 ewoks", flux.getContent());
    }

    @Test
    public final void applyWithParenthesesMultiplication() throws CommandException {
        final String line = "I Love 5 * (2 + 3) ewoks";
        final CommandFlow flux = operatorManager.applyOperators(null, CommandFlowBuilder.create(line));
        assertEquals("I Love 25.0 ewoks", flux.getContent());
    }

    @Test
    public final void applyWithNegativeMultiplication() throws CommandException {
        final String line = "I Love -1 * 10 ewoks";
        final CommandFlow flux = operatorManager.applyOperators(null, CommandFlowBuilder.create(line));
        assertEquals("I Love -10.0 ewoks", flux.getContent());
    }

    @Test
    public final void applyWithNegativeMultiplication2() throws CommandException {
        final String line = "I Love -5 * -5 ewoks";
        final CommandFlow flux = operatorManager.applyOperators(null, CommandFlowBuilder.create(line));
        assertEquals("I Love 25.0 ewoks", flux.getContent());
    }

}
