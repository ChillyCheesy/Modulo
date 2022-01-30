package com.chillycheesy.hometracker.commands;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FluxBuilder {

    public static String extractFromFlux(CommandFlux flux, String regex, String defaultValue) {
        final String content = flux.getContent().trim();
        final Pattern pattern = Pattern.compile(regex);
        final Matcher matcher = pattern.matcher(content);
        return matcher.find() ? matcher.group() : defaultValue;
    }

    public static String extractFromFlux(CommandFlux flux, String regex) {
        return extractFromFlux(flux, regex, "");
    }

    public static CommandFlux combine(AliasManager aliasManager, CommandFlux...fluxes) {
        final CommandFlux builtFLux = FluxBuilder.empty(aliasManager);
        for (CommandFlux flux : fluxes) {
            builtFLux.setContent(builtFLux.getContent() + flux.getContent());
            builtFLux.setSuccess(builtFLux.isSuccess() && flux.isSuccess());
        }
        return builtFLux;
    }

    public static CommandFlux empty(AliasManager aliasManager) {
        return create("", aliasManager);
    }

    public static CommandFlux create(String content, AliasManager aliasManager) {
        return new CommandFlux(content, aliasManager);
    }
}
