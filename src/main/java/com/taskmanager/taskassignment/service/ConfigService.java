package com.taskmanager.taskassignment.service;

import com.taskmanager.taskassignment.repository.ConfigTaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jakarta.annotation.PostConstruct;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class ConfigService {

    @Autowired
    private ConfigTaskRepository configTaskRepository;

    private final Map<String, String> configCache = new ConcurrentHashMap<>();

    @PostConstruct
    public void loadConfig() {
        refreshCache();
    }

    public void refreshCache() {
        configCache.clear();
        configTaskRepository.findAll().forEach(entity ->
                configCache.put(entity.getKey(), entity.getValue())
        );
    }

    public String getConfig(String key) {
        return configCache.getOrDefault(key, "Default Value");
    }
}