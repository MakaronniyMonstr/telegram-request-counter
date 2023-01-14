package com.vesko.telegram.handler;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Chat;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.User;
import com.pengrad.telegrambot.request.SendMessage;
import org.springframework.stereotype.Component;

@Component
public class StartCommandHandler implements CommandHandler {
    public static final String START = "/start";

    @Override
    public String getCommand() {
        return START;
    }

    @Override
    public void onCommand(TelegramBot telegramBot, Update update, Chat chat, User user) {
        telegramBot.execute(new SendMessage(chat.id(), "Start"));
    }
}
