package com.chillycheesy.hometracker.commands;

import com.chillycheesy.hometracker.ModuloAPI;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Utils class for building Flux commands.
 */
public class FluxBuilder {

    /**
     * Extracts the first group from the given regex or the defaultValue if the regex doesn't match.
     * @param flux The flux to extract from.
     * @param regex The regex to match.
     * @param defaultValue The default value to return if the regex doesn't match.
     * @return The first group from the given regex or the defaultValue if the regex doesn't match.
     */
    public static String extractFromFlux(CommandFlux flux, String regex, String defaultValue) {
        final String content = flux.getContent().trim();
        final Pattern pattern = Pattern.compile(regex);
        final Matcher matcher = pattern.matcher(content);
        return matcher.find() ? matcher.group() : defaultValue;
    }

    /**
     * Extracts the first group from the given regex.
     * @param flux The flux to extract from.
     * @param regex The regex to match.
     * @return The first group from the given regex.
     */
    public static String extractFromFlux(CommandFlux flux, String regex) {
        return extractFromFlux(flux, regex, "");
    }

    /**
     * Combine the given fluxes into a single flux.
     * @param aliasManager The alias manager to use.
     * @param fluxes The fluxes to combine.
     * @return The combined flux.
     */
    public static CommandFlux combine(AliasManager aliasManager, CommandFlux...fluxes) {
        final CommandFlux builtFLux = FluxBuilder.empty(aliasManager);
        for (CommandFlux flux : fluxes) {
            builtFLux.setContent(builtFLux.getContent() + flux.getContent());
            builtFLux.setSuccess(builtFLux.isSuccess() && flux.isSuccess());
        }
        return builtFLux;
    }

    /**
     * Create an empty flux.
     * @param aliasManager The alias manager to use.
     * @return An empty flux.
     */
    public static CommandFlux empty(AliasManager aliasManager) {
        return create(aliasManager, "");
    }

    /**
     * Create a flux with the given content.
     * @param aliasManager The alias manager to use.
     * @param content The content of the flux.
     * @return A flux with the given content.
     */
    public static CommandFlux create(AliasManager aliasManager, String content) {
        return new CommandFlux(content, aliasManager);
    }

    /**
     * Create a flux with the given content.
     * @param content The content of the flux.
     * @return A flux with the given content.
     */
    public static CommandFlux create(String content) {
        return create(ModuloAPI.getCommand().getMainAliasManager(), content);
    }
}
