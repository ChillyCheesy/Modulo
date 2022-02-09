package com.chillycheesy.modulo.commands.operators;

import com.chillycheesy.modulo.ModuloAPI;
import com.chillycheesy.modulo.commands.CommandFlow;
import com.chillycheesy.modulo.commands.CommandManager;
import com.chillycheesy.modulo.commands.natif.AliasCommand;
import com.chillycheesy.modulo.commands.natif.EchoCommand;
import com.chillycheesy.modulo.commands.natif.ReturnCommand;
import com.chillycheesy.modulo.commands.operator.natif.*;
import com.chillycheesy.modulo.utils.exception.CommandException;
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
        final CommandFlow flux = commandManager.applyCommand(line);
        assertEquals("Wicket", flux.getAliasManager().getValue("ewok"));
    }

    @Test
    public final void testWithInsertAliasIntoBlock() throws CommandException {
        final String line =
                "ewok = Wicket;" +
                "echo {" +
                "   planet = Endor;" +
                "   return planet" +
                "}";
        final CommandFlow flux = commandManager.applyCommand(line);
        assertEquals("Endor", flux.getContent());
        assertFalse(flux.getAliasManager().isAlias("planet"));
    }
}
