package com.chillycheesy.hometracker.commands.natif;

import com.chillycheesy.hometracker.commands.CommandFlux;
import com.chillycheesy.hometracker.commands.CommandListener;
import com.chillycheesy.hometracker.commands.builder.Description;
import com.chillycheesy.hometracker.commands.builder.Label;
import com.chillycheesy.hometracker.commands.builder.Usage;
import com.chillycheesy.hometracker.modules.Module;

@Label("return")
@Description("return the given text in the current flux")
@Usage("return <value> - return the given text")
public class ReturnCommand implements CommandListener {

    @Override
    public CommandFlux onCommand(Module caller, String label, String[] args, CommandFlux flux) {
        final String message = String.join(" ", args);
        flux.setContent(message);
        flux.setSuccess(true);
        return flux;
    }

}
