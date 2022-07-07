package com.ncc.service.token;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

@Component
@ConditionalOnProperty(name = "cache.enable", havingValue = "true")
public class RedisTokenCache implements TokenCache {
    private static final String TOKEN_SET = "_tokens";
    private static final String TOKEN_STORE_WHEN_EXISTS = "XX";
    private static final String TOKEN_STORE_WHEN_NOT_EXISTS = "NX";
    private static final String TOKEN_TTL_SECONDS = "EX";

    @Autowired
    private JedisPool pool;

    @Value("${redis.ttl}")
    private long ttl;

    @Override
    public void storeToken(String username, String token) {
        try (Jedis jedis = pool.getResource()) {
            jedis.set(username, token, TOKEN_STORE_WHEN_NOT_EXISTS, TOKEN_TTL_SECONDS, ttl);
            jedis.sadd(TOKEN_SET, token);
        }
    }

    @Override
    public void refreshToken(String username, String token) {
        try (Jedis jedis = pool.getResource()) {
            jedis.set(username, token, TOKEN_STORE_WHEN_EXISTS, TOKEN_TTL_SECONDS, ttl);
            jedis.sadd(TOKEN_SET, token);
        }
    }

    @Override
    public void storeToken(String username, String token, long expire) {
        try (Jedis jedis = pool.getResource()) {
            jedis.set(username, token, TOKEN_STORE_WHEN_NOT_EXISTS, TOKEN_TTL_SECONDS, expire);
            jedis.sadd(TOKEN_SET, token);
        }
    }

    @Override
    public void refreshToken(String username, String token, long expire) {
        try (Jedis jedis = pool.getResource()) {
            jedis.set(username, token, TOKEN_STORE_WHEN_EXISTS, TOKEN_TTL_SECONDS, expire);
            jedis.sadd(TOKEN_SET, token);
        }
    }

    @Override
    public void deleteToken(String token) {
        try (Jedis jedis = pool.getResource()) {
            jedis.srem(TOKEN_SET, token);
        }
    }

    @Override
    public void deleteTokenForUser(String username) {
        try (Jedis jedis = pool.getResource()) {
            String token = jedis.get(username);
            if (token != null) {
                jedis.del(username);
                jedis.srem(TOKEN_SET, token);
            }
        }
    }

    @Override
    public String getTokenByKey(String key) {
        String token = null;

        try (Jedis jedis = pool.getResource();) {
            token = jedis.get(key);
        }


        return token;
    }

    @Override
    public boolean isTokenStored(String token) {
        boolean tokenFound;

        try (Jedis jedis = pool.getResource()) {
            tokenFound = jedis.sismember(TOKEN_SET, token);

        }
        return tokenFound;
    }
}
