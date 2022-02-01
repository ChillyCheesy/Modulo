package com.chillycheesy.hometracker.commands.natif;

import com.chillycheesy.hometracker.ModuloAPI;
import com.chillycheesy.hometracker.commands.CommandFlux;
import com.chillycheesy.hometracker.commands.CommandListener;
import com.chillycheesy.hometracker.commands.builder.Description;
import com.chillycheesy.hometracker.commands.builder.Label;
import com.chillycheesy.hometracker.commands.builder.Usage;
import com.chillycheesy.hometracker.modules.Module;

@Label("echo")
@Description("Echos the given text")
@Usage("echo <text> - echos the given text")
public class EchoCommand implements CommandListener {

    @Override
    public CommandFlux onCommand(Module caller, String label, String[] args, CommandFlux flux) {
        final String message = String.join(" ", args);
        ModuloAPI.getLogger().info(caller, message);
        flux.setContent(message);
        flux.setSuccess(true);
        return flux;
    }

}
