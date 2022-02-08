package com.chillycheesy.hometracker.commands.operators;

import com.chillycheesy.hometracker.ModuloAPI;
import com.chillycheesy.hometracker.commands.CommandFlow;
import com.chillycheesy.hometracker.commands.builder.CommandFlowBuilder;
import com.chillycheesy.hometracker.commands.operator.OperatorManager;
import com.chillycheesy.hometracker.commands.operator.natif.LowerOrEqualsThanOperator;
import com.chillycheesy.hometracker.utils.exception.CommandException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LowerOrEqualsThanOperatorTest {

    private OperatorManager operatorManager;

    @BeforeEach
    public final void beforeEach() {
        operatorManager = ModuloAPI.getCommand().getOperatorManager();
        operatorManager.registerItemToBuild(null, new LowerOrEqualsThanOperator());
    }

    @AfterEach
    public final void afterEach() {
        ModuloAPI.getCommand().getOperatorManager().removeAllItems(null);
        ModuloAPI.getCommand().getCommandManager().removeAllItems(null);
    }

    @Test
    public final void applyWithNoLowerOrEqualsThan() throws CommandException {
        final String line = "I Love 6 ewoks";
        final CommandFlow flux = operatorManager.applyOperators(null, CommandFlowBuilder.create(line));
        assertEquals(line, flux.getContent());
    }

    @Test
    public final void applyWithSkipLowerOrEqualsThan() throws CommandException {
        final String line = "I Love 6 \\<= 10 ewoks";
        final CommandFlow flux = operatorManager.applyOperators(null, CommandFlowBuilder.create(line));
        assertEquals(line, flux.getContent());
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "I Love 3 <= 36 ewoks",
            "I Love 3 <=36 ewoks",
            "I Love 3<= 36 ewoks",
            "I Love 3<=36 ewoks",
            "I Love 3   <=36 ewoks",
            "I Love 3 <=     36 ewoks",
            "I Love 3 <=36 ewoks",
            "I Love 3\n<=\n36 ewoks",
            "I Love 3 \n<=\n     36 ewoks",
            "I Love 3   \n<=36 ewoks",
            "I Love 3   <=\n36 ewoks",
            "I Love 3<=  \n  36 ewoks",
            "I Love 3 <= 3 ewoks",
            "I Love 3 <=3 ewoks",
            "I Love 3<= 3 ewoks",
            "I Love 3<=3 ewoks",
            "I Love 3   <=3 ewoks",
            "I Love 3 <=     3 ewoks",
            "I Love 3 <=3 ewoks",
            "I Love 3\n<=\n3 ewoks",
            "I Love 3 \n<=\n     3 ewoks",
            "I Love 3   \n<=3 ewoks",
            "I Love 3   <=\n3 ewoks",
            "I Love 3<=  \n  3 ewoks"
    })
    public final void applyWithSimpleLowerOrEqualsThan(String line) throws CommandException {
        final CommandFlow flux = operatorManager.applyOperators(null, CommandFlowBuilder.create(line));
        assertEquals("I Love true ewoks", flux.getContent());
    }

    @Test
    public final void applyWithNotLowerOrEqualsThan() throws CommandException {
        final String line = "I Love 3 <= 1 ewoks";
        final CommandFlow flux = operatorManager.applyOperators(null, CommandFlowBuilder.create(line));
        assertEquals("I Love false ewoks",flux.getContent());
    }
}
