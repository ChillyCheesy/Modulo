package com.chillycheesy.modulo.commands.natif;

import com.chillycheesy.modulo.commands.AliasManager;
import com.chillycheesy.modulo.commands.CommandFlow;
import com.chillycheesy.modulo.commands.CommandListener;
import com.chillycheesy.modulo.commands.builder.CommandFlowBuilder;
import com.chillycheesy.modulo.commands.builder.Description;
import com.chillycheesy.modulo.commands.builder.Label;
import com.chillycheesy.modulo.commands.builder.Usage;
import com.chillycheesy.modulo.modules.Module;

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
