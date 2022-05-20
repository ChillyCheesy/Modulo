package com.chillycheesy.modulo.commands.natif;

import com.chillycheesy.modulo.commands.builder.Label;
import com.chillycheesy.modulo.commands.builder.Usage;
import com.chillycheesy.modulo.ModuloAPI;
import com.chillycheesy.modulo.commands.CommandFlow;
import com.chillycheesy.modulo.commands.CommandListener;
import com.chillycheesy.modulo.commands.builder.Description;
import com.chillycheesy.modulo.modules.Module;

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
