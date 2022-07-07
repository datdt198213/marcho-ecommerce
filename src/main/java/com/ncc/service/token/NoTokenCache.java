package com.ncc.service.token;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnProperty(name = "cache.enable", havingValue = "false")
public class NoTokenCache implements TokenCache{
    @Override
    public void storeToken(String username, String token) {

    }

    @Override
    public void refreshToken(String username, String token) {

    }

    @Override
    public void storeToken(String username, String token, long expire) {

    }

    @Override
    public void refreshToken(String username, String token, long expire) {

    }

    @Override
    public void deleteToken(String token) {

    }

    @Override
    public void deleteTokenForUser(String username) {

    }

    @Override
    public String getTokenByKey(String key) {
        return null;
    }

    @Override
    public boolean isTokenStored(String token) {
        return true;
    }
}
