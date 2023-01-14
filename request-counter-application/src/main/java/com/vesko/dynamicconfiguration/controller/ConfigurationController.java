package com.vesko.dynamicconfiguration.controller;

import com.vesko.dynamicconfiguration.dto.QueueDelayDto;
import com.vesko.dynamicconfiguration.service.ConfigurationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ConfigurationController {
    private final ConfigurationService configurationService;

    @PostMapping("/updateQueueDelay")
    public void updateQueueDelay(@Valid @RequestBody QueueDelayDto queueDelayDto) {
        configurationService.setDelay(queueDelayDto.getDelay());
    }
}
