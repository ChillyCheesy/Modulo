package com.chillycheesy.hometracker.commands;

public class FluxBuilder {

    public static CommandFlux combine(CommandFlux...fluxes) {
        final CommandFlux builtFLux = new CommandFlux("");
        for (CommandFlux flux : fluxes) {
            builtFLux.setContent(builtFLux.getContent() + flux.getContent());
            builtFLux.setSuccess(builtFLux.isSuccess() && flux.isSuccess());
        }
        return builtFLux;
    }

}
