package com.vesko.telegram.handler;

import com.vesko.telegram.parser.TelegramCommand;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.DefaultAbsSender;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import static com.vesko.telegram.parser.TelegramCommand.unknown;

@Component
public class UnknownCommandHandler implements CommandHandler {
    @Override
    public TelegramCommand getCommand() {
        return unknown();
    }

    @Override
    public void onCommand(DefaultAbsSender bot, Update update) throws TelegramApiException {
        var message = update.getMessage();
        var chatId = message.getChatId().toString();
        if (message.hasText()) {
            bot.execute(new SendMessage(chatId, "Я не знаю такой команды " + message.getText()));
        }
    }
}
