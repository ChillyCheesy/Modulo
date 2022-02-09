package com.chillycheesy.modulo.utils.exception;

import com.chillycheesy.modulo.commands.CommandFlow;

import java.util.Arrays;

public class CommandException extends Exception {

    public CommandException(CommandFlow flux, int index, String message) {
        super(message + "\n" +
            "CommandException: " + flux.getContent() + "\n" +
            "                  " + String.valueOf(CommandException.buildSpaces(index)) + "^\n"
        );
    }

    private static char[] buildSpaces(int length) {
        final char[] spaces = new char[length];
        Arrays.fill(spaces, ' ');
        return spaces;
    }

}
