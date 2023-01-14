package com.vesko.telegram.parser;

import java.util.List;

public record TelegramCommand(String command, List<String> args) {
}
