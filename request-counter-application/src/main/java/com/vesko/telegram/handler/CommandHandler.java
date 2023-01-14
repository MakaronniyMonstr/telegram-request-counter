package com.vesko.telegram.handler;

import org.telegram.telegrambots.bots.DefaultAbsSender;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public interface CommandHandler {
    String getCommand();

    void onCommand(DefaultAbsSender bot, Update update) throws TelegramApiException;
}
