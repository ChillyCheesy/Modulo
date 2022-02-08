package com.chillycheesy.hometracker.commands;

import com.chillycheesy.hometracker.ModuloAPI;
import com.chillycheesy.hometracker.commands.natif.EchoCommand;
import com.chillycheesy.hometracker.commands.operator.OperatorManager;
import com.chillycheesy.hometracker.commands.operator.natif.ParenthesesOperator;
import com.chillycheesy.hometracker.commands.operator.natif.PlusOperator;
import com.chillycheesy.hometracker.commands.operator.natif.SkipOperator;
import com.chillycheesy.hometracker.utils.exception.CommandException;
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

    @Test
    public final void testRunCommand() throws CommandException {
        final CommandFlow flux = commandManager.applyCommand("echo arg1 '6 + 7' \"6 + 7\"");
        assertEquals("arg1 6 + 7 13.0", flux.getContent());
    }
}
