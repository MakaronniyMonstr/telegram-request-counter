package com.vesko.telegram.parser;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.regex.Pattern;

import static java.util.Collections.emptyList;

@Component
public class TelegramCommandParser {
    private static final Pattern COMMAND_PATTERN = Pattern.compile("(/\\w+\\b)(.*)");
    private static final String COMMAND_ARGS_DELIMITER = " ";

    public TelegramCommand parseCommand(String message) throws NotCommandException {
        var matcher = COMMAND_PATTERN.matcher(message);
        if (!matcher.find()) {
            throw new NotCommandException();
        }

        var command = matcher.group(1);
        var argsLine = matcher.group(2);
        List<String> args = argsLine == null || argsLine.isEmpty() || argsLine.isBlank()
                ? emptyList()
                : List.of(argsLine.trim().split(COMMAND_ARGS_DELIMITER));

        return new TelegramCommand(command, args);
    }
}
