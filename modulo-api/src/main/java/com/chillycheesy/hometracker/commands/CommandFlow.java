package com.chillycheesy.hometracker.commands;

/**
 * This class is used to define the flow of commands.
 * A flow represent a series of commands and operators that are executed in sequence.
 * a flow have a success status, which is true if all commands in the flow executed successfully.
 *
 * Each command in the flow is executed in sequence with a personal {@link AliasManager}.
 */
public class CommandFlow {

    /**
     * The list of commands in the flow.
     */
    private String content;
    /**
     * The alias manager used to execute the commands in the flow.
     */
    private AliasManager aliasManager;
    /**
     * The success status of the flow.
     */
    private boolean success;

    /**
     * Constructor.
     * @param content the content of the flow.
     * @param aliasManager the alias manager used to execute the commands in the flow.
     */
    public CommandFlow(String content, AliasManager aliasManager) {
        this(content, aliasManager, true);
    }

    /**
     * Constructor.
     * @param content the content of the flow.
     * @param aliasManager the alias manager used to execute the commands in the flow.
     * @param success the success status of the flow.
     */
    public CommandFlow(String content, AliasManager aliasManager, boolean success) {
        this.content = content;
        this.aliasManager = aliasManager;
        this.success = success;
    }

    /**
     * Set the content of the flow.
     * @param content the content of the flow.
     */
    public void setContent(String content) {
        this.content = content;
    }

    /**
     * Get the content of the flow.
     * @return the success status of the flow.
     */
    public String getContent() {
        return content;
    }

    /**
     * Set the alias manager used to execute the commands in the flow.
     * @param aliasManager the alias manager used to execute the commands in the flow.
     */
    public void setAliasManager(AliasManager aliasManager) {
        this.aliasManager = aliasManager;
    }

    /**
     * Get the alias manager used to execute the commands in the flow.
     * @return the success status of the flow.
     */
    public AliasManager getAliasManager() {
        return aliasManager;
    }

    /**
     * Set the success status of the flow.
     * @param success the success status of the flow.
     */
    public void setSuccess(boolean success) {
        this.success = success;
    }

    /**
     * Get the success status of the flow.
     * @return the success status of the flow.
     */
    public boolean isSuccess() {
        return success;
    }

    @Override
    public String toString() {
        return "CommandFlux{" +
                "content='" + content + '\'' +
                ", success=" + success +
                '}';
    }
}
