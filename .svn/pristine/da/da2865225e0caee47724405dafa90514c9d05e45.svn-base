package com.sumridge.smart.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

/**
 * Created by liu on 16/8/24.
 */
@Service
public class CacheService {
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    public String getValue(String key) {
        return stringRedisTemplate.opsForValue().get(key);
    }
}
