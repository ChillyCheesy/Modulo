package com.chillycheesy.hometracker.commands.natif;

import com.chillycheesy.hometracker.commands.AliasManager;
import com.chillycheesy.hometracker.commands.CommandFlux;
import com.chillycheesy.hometracker.commands.CommandListener;
import com.chillycheesy.hometracker.commands.FluxBuilder;
import com.chillycheesy.hometracker.commands.builder.Description;
import com.chillycheesy.hometracker.commands.builder.Label;
import com.chillycheesy.hometracker.commands.builder.Usage;
import com.chillycheesy.hometracker.modules.Module;

@Label("alias")
@Description("Create an alias for a command")
@Usage("alias <name> <value>")
public class AliasCommand implements CommandListener {

    @Override
    public CommandFlux onCommand(Module caller, String label, String[] args, CommandFlux flux) {
        final AliasManager aliasManager = flux.getAliasManager();
        if (args.length == 2) {
            aliasManager.registerAlias(args[0], args[1]);
            return FluxBuilder.create("'" + args[1] + "'", aliasManager);
        }
        flux.setSuccess(false);
        return flux;
    }

}
