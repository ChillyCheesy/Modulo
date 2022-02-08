package com.chillycheesy.hometracker.commands.natif;

import com.chillycheesy.hometracker.ModuloAPI;
import com.chillycheesy.hometracker.commands.CommandFlow;
import com.chillycheesy.hometracker.commands.CommandListener;
import com.chillycheesy.hometracker.commands.builder.Description;
import com.chillycheesy.hometracker.commands.builder.Label;
import com.chillycheesy.hometracker.commands.builder.Usage;
import com.chillycheesy.hometracker.modules.Module;

/**
 * The EchoCommand prints the arguments passed to it in the result CommandFlow and in the console output.
 *
 */
@Label("echo")
@Description("Echos the given text")
@Usage("echo <text> - echos the given text")
public class EchoCommand implements CommandListener {

    @Override
    public CommandFlow onCommand(Module caller, String label, String[] args, CommandFlow flux) {
        final String message = String.join(" ", args);
        ModuloAPI.getLogger().info(caller, message);
        flux.setContent(message);
        flux.setSuccess(true);
        return flux;
    }

}
