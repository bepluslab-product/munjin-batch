package com.beplushealthcare.squereplus.batch.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class RedisService {

    private final RedisTemplate<String, String> redisTemplate;

    public void setRedisValue(String key, String value, long ttl) {
        redisTemplate.opsForValue().set(key, value, Duration.ofSeconds(ttl));
    }

    public void setRedisValueOfMillis(String key, String value, long ttl) {
        redisTemplate.opsForValue().set(key, value, Duration.ofMillis(ttl));
    }

    public String getRedisValue(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    public boolean hasKey(String key) {
        return redisTemplate.hasKey(key);
    }
    public void deleteRedis(String key) {
        redisTemplate.delete(key);
    }

    public void deleteAllRedis(List<String> keys) {
        redisTemplate.delete(keys);
    }
}
