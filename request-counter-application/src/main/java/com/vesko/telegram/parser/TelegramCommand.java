package com.vesko.telegram.parser;

import java.util.List;

import static java.util.Collections.emptyList;

public record TelegramCommand(String command, List<String> args) {
    private static final TelegramCommand EMPTY = new TelegramCommand("EMPTY", emptyList());

    public TelegramCommand(String command) {
        this(command, emptyList());
    }

    public static TelegramCommand empty() {
        return EMPTY;
    }
}
