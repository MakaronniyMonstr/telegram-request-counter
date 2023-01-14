package com.vesko.dynamicconfiguration.service.impl;

import com.vesko.dynamicconfiguration.service.ConfigurationService;
import org.springframework.stereotype.Service;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Service
public class ConfigurationServiceImpl implements ConfigurationService {
    public static final String DELAY = "DELAY";
    private final ConcurrentMap<String, Object> properties = new ConcurrentHashMap<>();

    @Override
    public void setDelay(Long delay) {
        properties.put(DELAY, delay);
    }

    @Override
    public Long getDelay() {
        return (Long) properties.get(DELAY);
    }
}
