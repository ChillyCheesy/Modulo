package com.chillycheesy.hometracker.commands.operators;

import com.chillycheesy.hometracker.ModuloAPI;
import com.chillycheesy.hometracker.commands.CommandFlow;
import com.chillycheesy.hometracker.commands.builder.CommandFlowBuilder;
import com.chillycheesy.hometracker.commands.natif.EchoCommand;
import com.chillycheesy.hometracker.commands.operator.OperatorManager;
import com.chillycheesy.hometracker.commands.operator.natif.ParenthesesOperator;
import com.chillycheesy.hometracker.commands.operator.natif.SkipOperator;
import com.chillycheesy.hometracker.utils.exception.CommandException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SkipOperatorTest {

    private OperatorManager operatorManager;

    @BeforeEach
    public final void beforeEach() {
        operatorManager = ModuloAPI.getCommand().getOperatorManager();
        operatorManager.registerItemToBuild(null,  new ParenthesesOperator(), new SkipOperator());
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
        final String line = "I '(Love ewoks)'";
        final CommandFlow flux = operatorManager.applyOperators(null, CommandFlowBuilder.create(line));
        assertEquals("I \"\\(\\L\\o\\v\\e\\ \\e\\w\\o\\k\\s\\)\"", flux.getContent());
    }

}