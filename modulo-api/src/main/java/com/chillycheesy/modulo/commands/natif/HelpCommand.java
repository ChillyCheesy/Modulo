package com.chillycheesy.modulo.commands.natif;

import com.chillycheesy.modulo.ModuloAPI;
import com.chillycheesy.modulo.commands.Command;
import com.chillycheesy.modulo.commands.CommandFlow;
import com.chillycheesy.modulo.commands.CommandListener;
import com.chillycheesy.modulo.commands.builder.Description;
import com.chillycheesy.modulo.commands.builder.Label;
import com.chillycheesy.modulo.commands.builder.Usage;
import com.chillycheesy.modulo.modules.Module;

import javax.management.monitor.CounterMonitorMBean;


/**
 * The HelpCommand prints the list of the server's command in the result CommandFlux and in the console output
 */
@Label("help")
@Description("Return the list of server's commands")
@Usage("help - Return the list of server's commands")
public class HelpCommand implements CommandListener {

    @Override
    public CommandFlow onCommand(Module caller, String label, String[] args, CommandFlow flow) {
        String commands = args.length == 0 ? getAllCommand() : getCommand(args[0]);
        ModuloAPI.getLogger().info(caller,commands);
        flow.setContent(commands);
        flow.setSuccess(true);
        return flow;
    }

    private String getAllCommand(){
        String commands = "HELP\n";
        for(Command c: ModuloAPI.getCommand().getCommandManager().getAllItems()){
            commands += "\t"+c.getLabel()+" : "+c.getDescription()+"\n";
        }
        return commands;
    }

    private String getCommand(String label){
        String command = "HELP\n";
        Command c = ModuloAPI.getCommand().getCommandManager().getCommandByLabel(label);
        command += "\t"+c.getLabel()+" : "+c.getDescription()+"\n";
        return command;
    }
}
