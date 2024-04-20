package com.example.socialweb.configurations.utils;

import com.example.socialweb.models.entities.User;
import com.example.socialweb.services.converters.UserConverter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
@Slf4j
public class Cache {
    private Map<String, byte[]> cacheMap;

    public void loadUser(User user) {
        cacheMap.put("user", UserConverter.serializeUser(user));
    }

    public User getUser() {
        if (cacheMap.containsKey("user"))
            return UserConverter.deserializeUser(cacheMap.get("user"));
        else
            return null;
    }

    public Cache() {
        cacheMap = new HashMap<>();
    }
}
