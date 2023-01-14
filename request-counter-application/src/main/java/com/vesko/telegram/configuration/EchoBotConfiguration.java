package com.vesko.telegram.configuration;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.vesko.telegram.properties.BotProperty;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Configuration
@EnableConfigurationProperties(BotProperty.class)
@RequiredArgsConstructor
public class EchoBotConfiguration {
    private final BotProperty botProperty;

    @Bean
    public TelegramBot telegramBot() {
        var bot = new TelegramBot(botProperty.bot_key());
        bot.setUpdatesListener(list -> UpdatesListener.CONFIRMED_UPDATES_ALL);
        return bot;
    }
}
