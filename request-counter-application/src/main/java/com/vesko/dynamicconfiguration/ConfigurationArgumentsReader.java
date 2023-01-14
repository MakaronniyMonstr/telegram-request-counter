package com.vesko.dynamicconfiguration;

import com.vesko.dynamicconfiguration.service.ConfigurationService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.stereotype.Component;

import static com.vesko.dynamicconfiguration.util.ArgumentReaderUtils.readRequiredSingleArgument;

@Component
@RequiredArgsConstructor
public class ConfigurationArgumentsReader {
    private static final String DEFAULT_DELAY = "default_delay";
    private final ConfigurationService configurationService;

    public void read(ApplicationArguments args) {
        String defaultDelay = readRequiredSingleArgument(args, DEFAULT_DELAY);
        configurationService.setDelay(Long.valueOf(defaultDelay));
    }
}
