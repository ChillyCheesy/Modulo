package com.chillycheesy.modulo.commands;

import com.chillycheesy.modulo.ModuloAPI;
import com.chillycheesy.modulo.commands.Command;
import com.chillycheesy.modulo.commands.CommandFlow;
import com.chillycheesy.modulo.commands.CommandManager;
import com.chillycheesy.modulo.commands.natif.EchoCommand;
import com.chillycheesy.modulo.commands.operator.OperatorManager;
import com.chillycheesy.modulo.commands.operator.natif.ParenthesesOperator;
import com.chillycheesy.modulo.commands.operator.natif.PlusOperator;
import com.chillycheesy.modulo.commands.operator.natif.SkipOperator;
import com.chillycheesy.modulo.utils.exception.CommandException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class EchoCommandTest {

    private CommandManager commandManager;

    @BeforeEach
    public final void beforeEach() {
        final OperatorManager operatorManager = ModuloAPI.getCommand().getOperatorManager();
        operatorManager.registerItemToBuild(null,
                new ParenthesesOperator(),
                new SkipOperator(),
                new PlusOperator()
        );
        commandManager = ModuloAPI.getCommand().getCommandManager();
        final Command echoCommand = new Command("echo");
        echoCommand.setCommandListener(new EchoCommand());
        commandManager.registerItem(null, echoCommand);
    }

    @AfterEach
    public final void clearCommandManager() {
        commandManager.getAllItems().clear();
    }

    @Test
    public final void testRunCommand() throws CommandException {
        final CommandFlow flux = commandManager.applyCommand("echo arg1 '6 + 7' \"6 + 7\"");
        assertEquals("arg1 6 + 7 13.0", flux.getContent());
    }
}
