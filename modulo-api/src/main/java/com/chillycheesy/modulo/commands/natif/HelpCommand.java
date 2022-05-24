package com.chillycheesy.modulo.commands.natif;

import com.chillycheesy.modulo.ModuloAPI;
import com.chillycheesy.modulo.commands.Command;
import com.chillycheesy.modulo.commands.CommandFlow;
import com.chillycheesy.modulo.commands.CommandListener;
import com.chillycheesy.modulo.commands.builder.Description;
import com.chillycheesy.modulo.commands.builder.Label;
import com.chillycheesy.modulo.commands.builder.Usage;
import com.chillycheesy.modulo.modules.Module;


/**
 * The HelpCommand prints the list of the server's command in the result CommandFlux and in the console output
 */
@Label("help")
@Description("Return the list of server's commands")
@Usage("help <command's name> - Return the list of server's commands or the description of the given command's")
public class HelpCommand implements CommandListener {

    @Override
    public CommandFlow onCommand(Module caller, String label, String[] args, CommandFlow flow) {
        final StringBuilder commands = new StringBuilder("HELP\n");
        commands.append(args.length == 0 ? getAllCommand() : getCommandByLabel(args[0]));
        ModuloAPI.getLogger().info(caller,commands.toString());
        flow.setContent(commands.toString());
        flow.setSuccess(true);
        return flow;
    }

    private String getAllCommand(){
        final StringBuilder commands = new StringBuilder();
        for (Command c: ModuloAPI.getCommand().getCommandManager().getAllItems()) {
            commands.append(getCommandByLabel(c.getLabel()));
        }
        return commands.toString();
    }

    private String getCommandByLabel(String label){
        final StringBuilder builder = new StringBuilder();
        final Command command = ModuloAPI.getCommand().getCommandManager().getCommandByLabel(label);
        builder.append("\t").append(command.getLabel()).append(" : ").append(command.getDescription()).append("\n");
        return builder.toString();
    }
}
