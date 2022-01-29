package com.chillycheesy.hometracker.commands;

import com.chillycheesy.hometracker.ModuloAPI;
import com.chillycheesy.hometracker.modules.Module;

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
