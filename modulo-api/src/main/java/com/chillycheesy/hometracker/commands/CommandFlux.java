package com.chillycheesy.hometracker.commands;

public class CommandFlux {

    private String content;
    private AliasManager aliasManager;
    private boolean success;

    public CommandFlux(String content, AliasManager aliasManager) {
        this(content, aliasManager, true);
    }

    public CommandFlux(String content, AliasManager aliasManager, boolean success) {
        this.content = content;
        this.aliasManager = aliasManager;
        this.success = success;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public void setAliasManager(AliasManager aliasManager) {
        this.aliasManager = aliasManager;
    }

    public AliasManager getAliasManager() {
        return aliasManager;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

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
