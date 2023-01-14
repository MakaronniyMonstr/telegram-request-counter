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

import java.util.HashSet;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Slf4j
@RestController
@RequiredArgsConstructor
public class MessageController {
    private final ConcurrentMap<String, HashSet<EchoMessageRequest>> namedMessageSets = new ConcurrentHashMap<>();
    private final EchoBot echoBot;
    private final UserInfoService userInfoService;
    private final ConfigurationService configurationService;

    @PostMapping("/receiveEchoMessage")
    public EchoMessageResponse receiveEchoMessage(@Validated @RequestBody EchoMessageRequest echoMessageRequest) throws InterruptedException {
        var delay = configurationService.getDelay();
        var username = echoMessageRequest.getTargetUsername();
        var userInfoDto = userInfoService.getAndIncrementMessageCounterBy(username);
        var messageCount = userInfoDto.messageCount();

        namedMessageSets.compute(username, (k, v) -> {
                    if (v == null) {
                        v = new HashSet<>();
                    }
                    return v;
                })
                .add(echoMessageRequest);
        log.info("Received and saved echo message from " + username);
        Thread.sleep(delay);
        namedMessageSets.get(username)
                .remove(echoMessageRequest);
        log.info("Removes echo message from " + username);

        try {
            echoBot.execute(new SendMessage(userInfoDto.chatId(), echoMessageRequest.getMessage() + " " + messageCount));
            Thread.sleep(delay);
            log.info("Sent echo message");
            return new EchoMessageResponse(true, messageCount);
        } catch (TelegramApiException e) {
            return new EchoMessageResponse(false, messageCount);
        }
    }
}
