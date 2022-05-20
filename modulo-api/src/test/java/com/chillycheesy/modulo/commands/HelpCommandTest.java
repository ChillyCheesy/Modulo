package com.chillycheesy.modulo.commands;

import com.chillycheesy.modulo.ModuloAPI;
import com.chillycheesy.modulo.commands.natif.EchoCommand;
import com.chillycheesy.modulo.commands.natif.HelpCommand;
import com.chillycheesy.modulo.commands.operator.OperatorManager;
import com.chillycheesy.modulo.commands.operator.natif.ParenthesesOperator;
import com.chillycheesy.modulo.commands.operator.natif.PlusOperator;
import com.chillycheesy.modulo.commands.operator.natif.SkipOperator;
import com.chillycheesy.modulo.utils.exception.CommandException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class HelpCommandTest {

    private CommandManager commandManager;

    @BeforeEach
    public final void beforeEach() {
        commandManager = ModuloAPI.getCommand().getCommandManager();
        final Command echoCommand = new Command("echo");
        echoCommand.setDescription("Echos the given text");
        echoCommand.setCommandListener(new EchoCommand());
        final Command helpCommand = new Command("help");
        helpCommand.setDescription("Return the list of server's commands");
        helpCommand.setCommandListener(new HelpCommand());
        commandManager.registerItem(null, echoCommand, helpCommand);
    }

    @Test
    public final void testRunCommand() throws CommandException {
        final CommandFlow flow = commandManager.applyCommand("help");
        assertEquals("HELP\n\techo : Echos the given text\n\thelp : Return the list of server's commands\n",flow.getContent());
    }

    @Test
    public final void testRunCommandWithParameter() throws CommandException {
        final CommandFlow flow = commandManager.applyCommand("help echo");
        assertEquals("HELP\n\techo : Echos the given text\n",flow.getContent());
    }
}
