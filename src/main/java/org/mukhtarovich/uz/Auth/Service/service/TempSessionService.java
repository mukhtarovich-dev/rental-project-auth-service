package org.mukhtarovich.uz.Auth.Service.service;

import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class TempSessionService {

    private final Map<String,String> sessions = new ConcurrentHashMap<>();

    public void save(String token, String username){
        sessions.put(token, username);
    }

    public String getUsername(String token){
        return sessions.get(token);
    }

    public void remove(String token){
        sessions.remove(token);
    }
}