package com.chillycheesy.modulo.commands.natif;

import com.chillycheesy.modulo.commands.CommandFlow;
import com.chillycheesy.modulo.commands.CommandListener;
import com.chillycheesy.modulo.commands.builder.Description;
import com.chillycheesy.modulo.commands.builder.Label;
import com.chillycheesy.modulo.commands.builder.Usage;
import com.chillycheesy.modulo.modules.Module;

/**
 * The ReturnCommand prints the arguments passed to it in the result CommandFlow.
 *
 */
@Label("return")
@Description("return the given arguments in the current flux")
@Usage("return <value> - return the given text")
public class ReturnCommand implements CommandListener {

    @Override
    public CommandFlow onCommand(Module caller, String label, String[] args, CommandFlow flow) {
        final String message = String.join(" ", args);
        flow.setContent(message);
        flow.setSuccess(true);
        return flow;
    }

}
