package com.chillycheesy.hometracker.commands.operators;

import com.chillycheesy.hometracker.ModuloAPI;
import com.chillycheesy.hometracker.commands.CommandFlux;
import com.chillycheesy.hometracker.commands.CommandListener;
import com.chillycheesy.hometracker.commands.CommandManager;
import com.chillycheesy.hometracker.commands.FluxBuilder;
import com.chillycheesy.hometracker.commands.builder.Description;
import com.chillycheesy.hometracker.commands.builder.Label;
import com.chillycheesy.hometracker.commands.builder.Usage;
import com.chillycheesy.hometracker.commands.natif.EchoCommand;
import com.chillycheesy.hometracker.commands.operator.OperatorManager;
import com.chillycheesy.hometracker.commands.operator.natif.CommandStatusOperator;
import com.chillycheesy.hometracker.commands.operator.natif.InstructionEndOperator;
import com.chillycheesy.hometracker.commands.operator.natif.ParenthesesOperator;
import com.chillycheesy.hometracker.modules.Module;
import com.chillycheesy.hometracker.utils.exception.CommandException;
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
        final String line = "[successCommand]";
        final CommandFlux flux = commandManager.applyCommand(line);
        assertEquals("I love true", flux.getContent());
    }

    @Test
    public void commandFailedTest() throws CommandException {
        final String line = "[failedCommand]";
        final CommandFlux flux = commandManager.applyCommand(line);
        assertEquals("false", flux.getContent());
    }

    @Test
    public void commandEchoTest() throws CommandException {
        final String line = "echo I Love [failedCommand]";
        final CommandFlux flux = commandManager.applyCommand(line);
        assertEquals("I Love false", flux.getContent());
    }


}

@Label("successCommand")
@Description("always success")
@Usage("successCommand - always success")
class SuccessCommand implements CommandListener {

    @Override
    public CommandFlux onCommand(Module caller, String label, String[] args, CommandFlux flux) {
        flux.setSuccess(true);
        return flux;
    }

}

@Label("failedCommand")
@Description("always failed")
@Usage("failedCommand - always failed")
class FailedCommand implements CommandListener {

    @Override
    public CommandFlux onCommand(Module caller, String label, String[] args, CommandFlux flux) {
        flux.setSuccess(false);
        return flux;
    }

}
