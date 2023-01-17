package com.vesko.telegram.property;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "echo.api")
public record EchoMessageProperty(String host) {
}
