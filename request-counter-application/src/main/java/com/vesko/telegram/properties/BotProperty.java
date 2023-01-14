package com.vesko.telegram.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties
public record BotProperty(String bot_key) {
}
