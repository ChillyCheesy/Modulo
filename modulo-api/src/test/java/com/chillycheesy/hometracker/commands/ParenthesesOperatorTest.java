package com.chillycheesy.hometracker.commands;

import com.chillycheesy.hometracker.ModuloAPI;
import com.chillycheesy.hometracker.commands.operator.*;
import com.chillycheesy.hometracker.utils.exception.CommandException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ParenthesesOperatorTest {

    private OperatorManager operatorManager;

    @BeforeEach
    public final void beforeEach() {
        operatorManager = ModuloAPI.getCommand().getOperatorManager();
        operatorManager.registerItemToBuild(null,  new ParenthesesOperator(), new SkipOperator());
    }

    @Test
    public final void testParentheses() throws CommandException {
        final String line = "I (Love ewoks)";
        final CommandFlux flux = operatorManager.applyOperators(null, FluxBuilder.create(line));
        assertEquals("I Love ewoks", flux.getContent());
    }

    @Test
    public final void testSkipParentheses() throws CommandException {
        final String line = "I \\(Love ewoks)";
        final CommandFlux flux = operatorManager.applyOperators(null, FluxBuilder.create(line));
        assertEquals("I \\(Love ewoks)", flux.getContent());
    }

    @Test
    public final void testSkipParentheses2() throws CommandException {
        final String line = "I (Love ewoks\\)";
        final CommandFlux flux = operatorManager.applyOperators(null, FluxBuilder.create(line));
        assertEquals("I (Love ewoks\\)", flux.getContent());
    }

    @Test
    public final void testSkipParentheses3() throws CommandException {
        final String line = "I '(Love ewoks)'";
        final CommandFlux flux = operatorManager.applyOperators(null, FluxBuilder.create(line));
        assertEquals("I '(Love ewoks)'", flux.getContent());
    }

    @Test
    public final void testSkipParentheses4() throws CommandException {
        final String line = "I '(Love ewoks')";
        final CommandFlux flux = operatorManager.applyOperators(null, FluxBuilder.create(line));
        assertEquals("I '(Love ewoks')", flux.getContent());
    }

    @Test
    public final void testSkipParentheses5() throws CommandException {
        final String line = "I (Love 'ewoks)'";
        final CommandFlux flux = operatorManager.applyOperators(null, FluxBuilder.create(line));
        assertEquals("I (Love 'ewoks)'", flux.getContent());
    }

}
