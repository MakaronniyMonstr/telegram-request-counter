package com.vesko.telegram.parser;

public class CommandNotFoundException extends Exception {
    public CommandNotFoundException() {
        super("Command not found");
    }
}
