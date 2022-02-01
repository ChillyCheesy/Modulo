package com.chillycheesy.hometracker.commands;

import com.chillycheesy.hometracker.modules.Module;
import com.chillycheesy.hometracker.utils.exception.CommandException;

public class AliasCommand implements CommandListener{

    @Override
    public CommandFlux onCommand(Module caller, String label, String[] args, CommandFlux flux) throws CommandException {
        final AliasManager aliasManager = flux.getAliasManager();
        boolean successAssign = args.length == 2;
        if (successAssign)
            aliasManager.addAlias(args[0], args[1]);
        flux.setSuccess(successAssign);
        return flux;
    }

}
