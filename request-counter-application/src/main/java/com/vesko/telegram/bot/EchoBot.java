package com.vesko.telegram.bot;

import com.vesko.telegram.service.impl.HandledCommandService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
@RequiredArgsConstructor
public class EchoBot extends TelegramLongPollingBot {
    @Value("${bot_key}")
    private String token;

    private final HandledCommandService echoBotCommandService;

    @Override
    public String getBotUsername() {
        return "bot";
    }

    @Override
    public String getBotToken() {
        return token;
    }

    @Override
    public void onUpdateReceived(Update update) {
        echoBotCommandService.execute(this, update);
    }
}
