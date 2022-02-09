package com.chillycheesy.modulo.commands.operators;

import com.chillycheesy.modulo.ModuloAPI;
import com.chillycheesy.modulo.commands.CommandFlow;
import com.chillycheesy.modulo.commands.builder.CommandFlowBuilder;
import com.chillycheesy.modulo.commands.natif.EchoCommand;
import com.chillycheesy.modulo.commands.operator.OperatorManager;
import com.chillycheesy.modulo.commands.operator.natif.ParenthesesOperator;
import com.chillycheesy.modulo.utils.exception.CommandException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ParenthesesOperatorTest {

    private OperatorManager operatorManager;

    @BeforeEach
    public final void beforeEach() {
        operatorManager = ModuloAPI.getCommand().getOperatorManager();
        operatorManager.registerItemToBuild(null,  new ParenthesesOperator());
        ModuloAPI.getCommand().getCommandManager().registerItemToBuild(null, new EchoCommand());
    }

    @AfterEach
    public final void afterEach() {
        ModuloAPI.getCommand().getOperatorManager().removeAllItems(null);
        ModuloAPI.getCommand().getCommandManager().removeAllItems(null);
    }

    @Test
    public final void testParentheses() throws CommandException {
        final String line = "I (echo Love ewoks)";
        final CommandFlow flux = operatorManager.applyOperators(null, CommandFlowBuilder.create(line));
        assertEquals("I Love ewoks", flux.getContent());
    }

    @Test
    public final void testSkipParentheses() throws CommandException {
        final String line = "I \\(Love ewoks)";
        final CommandFlow flux = operatorManager.applyOperators(null, CommandFlowBuilder.create(line));
        assertEquals("I \\(Love ewoks)", flux.getContent());
    }

    @Test
    public final void testSkipParentheses2() throws CommandException {
        final String line = "I (Love ewoks\\)";
        final CommandFlow flux = operatorManager.applyOperators(null, CommandFlowBuilder.create(line));
        assertEquals("I (Love ewoks\\)", flux.getContent());
    }

}
