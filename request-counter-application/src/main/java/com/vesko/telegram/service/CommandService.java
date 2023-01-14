package com.vesko.telegram.service;

import org.telegram.telegrambots.bots.DefaultAbsSender;
import org.telegram.telegrambots.meta.api.objects.Update;

public interface CommandService {
    void execute(DefaultAbsSender bot, Update update);
}
