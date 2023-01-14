package com.vesko.telegram.handler;

import com.vesko.userinfo.exception.UserAlreadyExistsException;
import com.vesko.userinfo.service.UserInfoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.DefaultAbsSender;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Slf4j
@Component
@RequiredArgsConstructor
public class StartCommandHandler implements CommandHandler {
    public static final String START = "/start";
    private final UserInfoService userInfoService;

    @Override
    public String getCommand() {
        return START;
    }

    @Override
    public void onCommand(DefaultAbsSender bot, Update update) throws TelegramApiException {
        var message = update.getMessage();
        var userName = message.getFrom().getUserName();
        var chatId = message.getChat().getId().toString();

        try {
            userInfoService.createUserInfo(chatId, userName);
            bot.execute(new SendMessage(chatId, "Пользователь " + userName + " , я тебя запомнил!"));
        } catch (UserAlreadyExistsException e) {
            bot.execute(new SendMessage(chatId, "Пользователь " + userName + " , я тебя знаю!"));
        }
    }
}
