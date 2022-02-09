package com.chillycheesy.modulo.commands.operators;

import com.chillycheesy.modulo.ModuloAPI;
import com.chillycheesy.modulo.commands.CommandFlow;
import com.chillycheesy.modulo.commands.CommandListener;
import com.chillycheesy.modulo.commands.CommandManager;
import com.chillycheesy.modulo.commands.builder.Description;
import com.chillycheesy.modulo.commands.builder.Label;
import com.chillycheesy.modulo.commands.builder.Usage;
import com.chillycheesy.modulo.commands.natif.EchoCommand;
import com.chillycheesy.modulo.commands.operator.natif.CommandStatusOperator;
import com.chillycheesy.modulo.modules.Module;
import com.chillycheesy.modulo.utils.exception.CommandException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CommandStatusOperatorTest {

    private CommandManager commandManager;


    @BeforeEach
    public final void beforeEach() {
        commandManager = ModuloAPI.getCommand().getCommandManager();
        ModuloAPI.getCommand().getOperatorManager().registerItemToBuild(null,  new CommandStatusOperator());
        commandManager.registerItemToBuild(null, new SuccessCommand(), new FailedCommand(), new EchoCommand());
    }

    @AfterEach
    public final void afterEach() {
        ModuloAPI.getCommand().getOperatorManager().removeAllItems(null);
        ModuloAPI.getCommand().getCommandManager().removeAllItems(null);
    }

    @Test
    public void commandSuccessTest() throws CommandException {
        final String line = "echo I love [successCommand]";
        final CommandFlow flux = commandManager.applyCommand(line);
        assertEquals("I love true", flux.getContent());
    }

    @Test
    public void commandFailedTest() throws CommandException {
        final String line = "[failedCommand]";
        final CommandFlow flux = commandManager.applyCommand(line);
        assertEquals("false", flux.getContent());
    }

    @Test
    public void commandEchoTest() throws CommandException {
        final String line = "echo I Love [failedCommand]";
        final CommandFlow flux = commandManager.applyCommand(line);
        assertEquals("I Love false", flux.getContent());
    }


}

@Label("successCommand")
@Description("always success")
@Usage("successCommand - always success")
class SuccessCommand implements CommandListener {

    @Override
    public CommandFlow onCommand(Module caller, String label, String[] args, CommandFlow flux) {
        flux.setSuccess(true);
        return flux;
    }

}

@Label("failedCommand")
@Description("always failed")
@Usage("failedCommand - always failed")
class FailedCommand implements CommandListener {

    @Override
    public CommandFlow onCommand(Module caller, String label, String[] args, CommandFlow flux) {
        flux.setSuccess(false);
        return flux;
    }

}
