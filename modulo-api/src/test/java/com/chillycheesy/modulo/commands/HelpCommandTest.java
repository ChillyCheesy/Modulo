package com.chillycheesy.modulo.commands;

import com.chillycheesy.modulo.ModuloAPI;
import com.chillycheesy.modulo.commands.natif.EchoCommand;
import com.chillycheesy.modulo.commands.natif.HelpCommand;
import com.chillycheesy.modulo.modules.Module;
import com.chillycheesy.modulo.utils.exception.CommandException;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

@Disabled
public class HelpCommandTest {

    private static CommandManager commandManager;

    @BeforeAll
    public static void beforeEach() {
        final Module module = mock(Module.class);
        commandManager = ModuloAPI.getCommand().getCommandManager();
        System.out.println(commandManager.getAllItems());
        final Command echoCommand = new Command("echo");
        echoCommand.setDescription("Echos the given text");
        echoCommand.setCommandListener(new EchoCommand());
        final Command helpCommand = new Command("help");
        helpCommand.setDescription("Return the list of server's commands");
        helpCommand.setCommandListener(new HelpCommand());
        commandManager.registerItem(module, echoCommand, helpCommand);
    }

    @AfterAll
    public static void clearCommandListener() {
        commandManager.getAllItems().clear();
    }

    @Test
    public final void testRunCommand() throws CommandException {
        final CommandFlow flow = commandManager.applyCommand("help");
        assertEquals("HELP\n" +
                "\techo : Echos the given text\n" +
                "\thelp : Return the list of server's commands\n", flow.getContent());
    }

    @Test
    public final void testRunCommandWithParameter() throws CommandException {
        final CommandFlow flow = commandManager.applyCommand("help echo");
        assertEquals("HELP\n" +
                "\techo : Echos the given text\n", flow.getContent());
    }
}
