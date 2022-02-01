package com.chillycheesy.hometracker.commands.operators;

import com.chillycheesy.hometracker.ModuloAPI;
import com.chillycheesy.hometracker.commands.CommandFlux;
import com.chillycheesy.hometracker.commands.CommandManager;
import com.chillycheesy.hometracker.commands.natif.AliasCommand;
import com.chillycheesy.hometracker.commands.natif.EchoCommand;
import com.chillycheesy.hometracker.commands.natif.ReturnCommand;
import com.chillycheesy.hometracker.commands.operator.natif.*;
import com.chillycheesy.hometracker.utils.exception.CommandException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class BlockOperatorTest {

    private CommandManager commandManager;

    @BeforeEach
    public final void beforeEach() {
        commandManager = ModuloAPI.getCommand().getCommandManager();
        ModuloAPI.getCommand().getOperatorManager().registerItemToBuild(null, new InstructionEndOperator(), new BlockOperator(), new CreateAliasOperator(), new CreateStrictAliasOperator(), new AliasOperator());
        commandManager.registerItemToBuild(null, new EchoCommand(), new AliasCommand(), new ReturnCommand());
    }

    @AfterEach
    public final void afterEach() {
        ModuloAPI.getCommand().getOperatorManager().removeAllItems(null);
        ModuloAPI.getCommand().getCommandManager().removeAllItems(null);
    }

    @Test
    public final void testWithNoInsertAlias() throws CommandException {
        final String line = "ewok = Wicket";
        final CommandFlux flux = commandManager.applyCommand(line);
        assertEquals("Wicket", flux.getAliasManager().getValue("ewok"));
    }

    @Test
    public final void testWithInsertAliasIntoBlock() throws CommandException {
        final String line = "ewok = Wicket; " +
                "echo {" +
                "   planet = Endor;" +
                "   return planet" +
                "}";
        final CommandFlux flux = commandManager.applyCommand(line);
        assertEquals("Endor", flux.getContent());
        assertFalse(flux.getAliasManager().isAlias("planet"));
    }
}
