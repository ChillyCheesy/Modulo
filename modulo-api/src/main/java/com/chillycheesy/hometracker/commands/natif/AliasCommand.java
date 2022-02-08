package com.chillycheesy.hometracker.commands.natif;

import com.chillycheesy.hometracker.commands.AliasManager;
import com.chillycheesy.hometracker.commands.CommandFlow;
import com.chillycheesy.hometracker.commands.CommandListener;
import com.chillycheesy.hometracker.commands.builder.CommandFlowBuilder;
import com.chillycheesy.hometracker.commands.builder.Description;
import com.chillycheesy.hometracker.commands.builder.Label;
import com.chillycheesy.hometracker.commands.builder.Usage;
import com.chillycheesy.hometracker.modules.Module;

/**
 * Command to add an alias to the alias manager.
 */
@Label("alias")
@Description("Create an alias for a command")
@Usage("alias <name> <value>")
public class AliasCommand implements CommandListener {

    @Override
    public CommandFlow onCommand(Module caller, String label, String[] args, CommandFlow flux) {
        final AliasManager aliasManager = flux.getAliasManager();
        if (args.length == 2) {
            aliasManager.registerAlias(args[0], args[1]);
            return CommandFlowBuilder.create(aliasManager, "'" + args[1] + "'");
        }
        flux.setSuccess(false);
        return flux;
    }

}
