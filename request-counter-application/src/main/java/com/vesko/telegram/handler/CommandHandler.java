package com.vesko.telegram.handler;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Chat;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.User;

public interface CommandHandler {
    String getCommand();

    void onCommand(TelegramBot telegramBot, Update update, Chat chat, User user);
}
