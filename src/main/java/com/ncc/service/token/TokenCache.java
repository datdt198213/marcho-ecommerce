package com.ncc.service.token;

public interface TokenCache {
    void storeToken(String username, String token);

    void refreshToken(String username, String token);

    void storeToken(String username, String token, long expire);

    void refreshToken(String username, String token, long expire);

    void deleteToken(String token);

    void deleteTokenForUser(String username);

    String getTokenByKey(String key);

    boolean isTokenStored(String token);
}
