package com.vesko.telegram.handler;

import com.vesko.application.exception.ResourceNotFoundException;
import com.vesko.telegram.dto.EchoMessageRequest;
import com.vesko.telegram.parser.TelegramCommand;
import com.vesko.telegram.service.impl.ScheduledEchoService;
import com.vesko.userinfo.service.UserInfoService;
import jakarta.annotation.Resource;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.DefaultAbsSender;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import static com.vesko.telegram.parser.TelegramCommand.empty;
import static org.springframework.util.Assert.notNull;

@Slf4j
@Component
@RequiredArgsConstructor
public class NoCommandHandler implements CommandHandler {
    private final ScheduledEchoService scheduledEchoService;
    private final UserInfoService userInfoService;

    @Override
    public TelegramCommand getCommand() {
        return empty();
    }

    @Override
    public void onCommand(DefaultAbsSender bot, Update update) throws TelegramApiException {
        var message = update.getMessage();
        var username = message.getFrom().getUserName();
        var chatId = message.getChat().getId().toString();
        notNull(username, "Username must not be null");

        if (!message.hasText()) {
            log.info("No text in message from {}", username);
        }
        try {
            // Проверяем, зарегестрирован ли пользователь
            userInfoService.findBy(username);
        } catch (ResourceNotFoundException e) {
            // Если пользователя нет в системе, предлагаем ему зарегестрироваться
            bot.execute(new SendMessage(chatId, "Я тебя не знаю. Сначала используй /start"));
            log.info("User {} not found. Should register first", username);
        }

        scheduledEchoService.schedule(
                new EchoMessageRequest(
                        message.getText(),
                        username
                )
        );
    }
}
