package com.vesko.telegram.service.impl;

import com.vesko.telegram.handler.CommandHandler;
import com.vesko.telegram.parser.NotCommandException;
import com.vesko.telegram.parser.TelegramCommand;
import com.vesko.telegram.parser.TelegramCommandParser;
import com.vesko.telegram.service.CommandService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.bots.DefaultAbsSender;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.vesko.telegram.parser.TelegramCommand.empty;
import static com.vesko.telegram.parser.TelegramCommand.unknown;

@Slf4j
@Service
public class HandledCommandService implements CommandService {
    private final Map<String, CommandHandler> commandHandlerMap;
    private final TelegramCommandParser commandParser;

    public HandledCommandService(List<CommandHandler> commandHandlers, TelegramCommandParser commandParser) {
        this.commandHandlerMap = commandHandlers.stream()
                .collect(Collectors.toConcurrentMap(
                        handler -> handler.getCommand().command(),
                        Function.identity()
                ));
        this.commandParser = commandParser;
    }

    @Override
    public void execute(DefaultAbsSender bot, Update update) {
        var message = update.getMessage();

        if (message != null && message.hasText()) {
            var text = message.getText();

            try {
                var command = commandParser.parseCommand(text);
                executeCommand(bot, update, command);
            } catch (NotCommandException e) {
                executeCommand(bot, update, empty());
            }
        }
    }

    private void executeCommand(DefaultAbsSender bot, Update update, TelegramCommand command) {
        try {
            resolveCommandHandler(command)
                    // ?? ????????????, ???????? ?????????????? ???????????????????????? ??????????????, ???????????????????? ???????????????????? ?? UnknownCommandHandler
                    .orElse(commandHandlerMap.get(unknown().command()))
                    .onCommand(bot, update);
        } catch (TelegramApiException e) {
            log.error("Message can not be delivered", e);
        }
    }

    protected Optional<CommandHandler> resolveCommandHandler(TelegramCommand telegramCommand) {
        return Optional.ofNullable(commandHandlerMap.get(telegramCommand.command()));
    }
}
