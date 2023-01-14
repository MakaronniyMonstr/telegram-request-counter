package com.vesko.telegram.parser;

public class NotCommandException extends Exception {
    public NotCommandException() {
        super("Message is not a valid command");
    }
}
