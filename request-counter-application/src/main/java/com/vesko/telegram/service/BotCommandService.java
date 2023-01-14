package com.vesko.telegram.service;

import com.pengrad.telegrambot.model.Update;
import com.vesko.telegram.handler.CommandHandler;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class BotCommandService {
    @Getter
    Map<String, CommandHandler> commandHandlerMap;

    @Autowired
    public BotCommandService(List<CommandHandler> commandHandlers) {
        this.commandHandlerMap = commandHandlers.stream()
                .collect(Collectors.toConcurrentMap(
                        CommandHandler::getCommand,
                        Function.identity()
                ));
    }

    public void execute(Update update) {
        var chat = update.message().chat();
        var user = update.message().from();

    }
}
