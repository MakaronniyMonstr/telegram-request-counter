package com.vesko.telegram.controller;

import com.vesko.dynamicconfiguration.service.ConfigurationService;
import com.vesko.telegram.bot.EchoBot;
import com.vesko.telegram.dto.EchoMessageRequest;
import com.vesko.telegram.dto.EchoMessageResponse;
import com.vesko.userinfo.service.UserInfoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Slf4j
@RestController
@RequiredArgsConstructor
public class MessageController {
    private final EchoBot echoBot;
    private final UserInfoService userInfoService;
    private final ConfigurationService configurationService;
    private final ScheduledExecutorService scheduledExecutorService;

    @PostMapping("/receiveEchoMessage")
    public EchoMessageResponse receiveEchoMessage(@Validated @RequestBody EchoMessageRequest echoMessageRequest) throws InterruptedException, ExecutionException {
        var delay = configurationService.getDelay();
        var username = echoMessageRequest.getTargetUsername();
        var userInfoDto = userInfoService.getAndIncrementMessageCounterBy(username);
        var messageCount = userInfoDto.messageCount();

        return scheduledExecutorService.schedule(() -> {
                            try {
                                // Пытаемся отправить echo message обратно telegram пользователю
                                echoBot.execute(new SendMessage(userInfoDto.chatId(), echoMessageRequest.getMessage() + " " + messageCount));
                                // В случае успешной отправки сообщения
                                return new EchoMessageResponse(true, messageCount);
                            } catch (TelegramApiException e) {
                                // В случае, если не удалось отправить сообщение
                                return new EchoMessageResponse(false, messageCount);
                            }
                        }, delay, TimeUnit.MILLISECONDS
                )
                .get();
    }
}
