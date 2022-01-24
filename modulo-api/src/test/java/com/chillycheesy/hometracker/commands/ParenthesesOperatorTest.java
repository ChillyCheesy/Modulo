package com.chillycheesy.hometracker.commands;

import com.chillycheesy.hometracker.ModuloAPI;
import com.chillycheesy.hometracker.commands.operator.OperatorManager;
import com.chillycheesy.hometracker.commands.operator.ParenthesesOperator;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ParenthesesOperatorTest {

    private CommandManager commandManager;

    @BeforeEach
    public final void beforeEach() {
        final OperatorManager operatorManager = ModuloAPI.getCommand().getOperatorManager();
        commandManager = ModuloAPI.getCommand().getCommandManager();
        operatorManager.registerItemToBuild(null, new ParenthesesOperator());
    }

    @Test
    public final void applyWithNoParentheses() {
        final String line = "I Love ewoks";
        final CommandFlux flux = commandManager.applyCommand(line);
        assertEquals(line, flux.getContent());
    }

    @Test
    public final void applyWithSimpleParentheses() {
        final String line = "I Love (ewoks)";
        final CommandFlux flux = commandManager.applyCommand(line);
        assertEquals("I Love ewoks", flux.getContent());
    }

    @Test
    public final void applyWithDoubleParentheses() {
        final String line = "(I Love) (ewoks)";
        final CommandFlux flux = commandManager.applyCommand(line);
        assertEquals("I Love ewoks", flux.getContent());
    }

    @Test
    public final void applyWithSubParentheses() {
        final String line = "(I Lo(v(e))) ((ew)oks)";
        final CommandFlux flux = commandManager.applyCommand(line);
        assertEquals("I Love ewoks", flux.getContent());
    }

    @Test
    public final void applyWithUncloseParentheses() {
        final String line = "(I Lo(v(e ((ewoks";
        final CommandFlux flux = commandManager.applyCommand(line);
        assertEquals("(I Lo(v(e ((ewoks", flux.getContent());
    }

    @Test
    public final void applyWithUnOpenParentheses() {
        final String line = "(I Lo(v(e ((ewoks";
        final CommandFlux flux = commandManager.applyCommand(line);
        assertEquals("(I Lo(v(e ((ewoks", flux.getContent());
    }

    @Test
    public final void applyWithDoubleAndUncloseParentheses() {
        final String line = "I Love) ew)oks)";
        final CommandFlux flux = commandManager.applyCommand(line);
        assertEquals("I Love) ew)oks)", flux.getContent());
    }

}
