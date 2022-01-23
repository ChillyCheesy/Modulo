package com.chillycheesy.hometracker.commands;

public class CommandFlux {

    private String content;
    private boolean success;

    public CommandFlux(String content) {
        this(content, true);
    }

    public CommandFlux(String content, boolean success) {
        this.content = content;
        this.success = success;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
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
