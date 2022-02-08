package com.chillycheesy.hometracker.commands.builder;

import com.chillycheesy.hometracker.ModuloAPI;
import com.chillycheesy.hometracker.commands.AliasManager;
import com.chillycheesy.hometracker.commands.CommandFlow;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Utils class for building a {@link CommandFlow}.
 */
public class CommandFlowBuilder {

    /**
     * Extracts the first group from the given regex or the defaultValue if the regex doesn't match.
     * @param flow The flow to extract from.
     * @param regex The regex to match.
     * @param defaultValue The default value to return if the regex doesn't match.
     * @return The first group from the given regex or the defaultValue if the regex doesn't match.
     */
    public static String extractFromFlux(CommandFlow flow, String regex, String defaultValue) {
        final String content = flow.getContent().trim();
        final Pattern pattern = Pattern.compile(regex);
        final Matcher matcher = pattern.matcher(content);
        return matcher.find() ? matcher.group() : defaultValue;
    }

    /**
     * Extracts the first group from the given regex.
     * @param flow The flow to extract from.
     * @param regex The regex to match.
     * @return The first group from the given regex.
     */
    public static String extractFromFlux(CommandFlow flow, String regex) {
        return extractFromFlux(flow, regex, "");
    }

    /**
     * Combine the given flows into a single flow.
     * @param aliasManager The alias manager to use.
     * @param flows The flows to combine.
     * @return The combined flow.
     */
    public static CommandFlow combine(AliasManager aliasManager, CommandFlow...flows) {
        final CommandFlow builtFLux = CommandFlowBuilder.empty(aliasManager);
        for (CommandFlow flux : flows) {
            builtFLux.setContent(builtFLux.getContent() + flux.getContent());
            builtFLux.setSuccess(builtFLux.isSuccess() && flux.isSuccess());
        }
        return builtFLux;
    }

    /**
     * Create an empty flow.
     * @param aliasManager The alias manager to use.
     * @return An empty flow.
     */
    public static CommandFlow empty(AliasManager aliasManager) {
        return create(aliasManager, "");
    }

    /**
     * Create a flow with the given content.
     * @param aliasManager The alias manager to use.
     * @param content The content of the flow.
     * @return A flow with the given content.
     */
    public static CommandFlow create(AliasManager aliasManager, String content) {
        return new CommandFlow(content, aliasManager);
    }

    /**
     * Create a flow with the given content.
     * @param content The content of the flow.
     * @return A flow with the given content.
     */
    public static CommandFlow create(String content) {
        return create(ModuloAPI.getCommand().getMainAliasManager(), content);
    }
}
