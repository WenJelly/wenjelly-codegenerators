package com.wenjelly.generatorbackend.manager;

/*
 * @time 2024/4/14 21:37
 * @package com.wenjelly.generatorbackend.manager
 * @project wenjelly-generator-web-backend
 * @author WenJelly
 */


import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * 多级缓存管理
 * Caffeine 一级缓存：将数据存储在应用程序的内存中，性能更高。但是仅在本地生效，而且应用程序关闭后，数据会丢失。
 * Redis 二级缓存：将数据存储在 Redis 中，所有的程序都从 Redis 内读取数据，可以实现数据的持久化和缓存的共享。
 * 二者结合，请求数据时，首先查找本地一级缓存；如果在本地缓存中没有查询到数据，再查找远程二级缓存，并且写入到本地缓存；如果还没有数据，才从数据库中读取，并且写入到所有缓存。
 * 使用多级缓存，可以充分利用本地缓存的快速读取特性，以及远程缓存的共享和持久化特性。
 */
@Component
public class CacheManager {

    // 本地缓存 (存储字符串)
    Cache<String, String> localCacheByString = Caffeine.newBuilder()
            .expireAfterWrite(100, TimeUnit.MINUTES)
            .maximumSize(10_000)
            .build();
    // 本地缓存 (存储对象)
    Cache<String, Object> localCacheByObject = Caffeine.newBuilder()
            .expireAfterWrite(100, TimeUnit.MINUTES)
            .maximumSize(10_000)
            .build();
    @Resource
    private StringRedisTemplate stringRedisTemplate;
    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    /**
     * 写缓存，通过字符串来存储
     *
     * @param key
     * @param value
     */
    public void put(String key, String value) {
        // 往本地写入缓存
        localCacheByString.put(key, value);
        // 往Redis写入缓存
        stringRedisTemplate.opsForValue().set(key, value, 120, TimeUnit.MINUTES);
    }

    /**
     * 写缓存，通过对象来存储，可以提高计算优化
     *
     * @param key
     * @param object
     */
    public void put(String key, Object object) {
        // 往本地写入缓存
        localCacheByObject.put(key, object);
        // 往Redis写入缓存
        redisTemplate.opsForValue().set(key, object, 120, TimeUnit.MINUTES);
    }

    /**
     * 读缓存，返回字符串类型
     *
     * @param key
     * @return
     */
    public String get(String key) {

        // 先从本地读取缓存
        String value = localCacheByString.getIfPresent(key);
        if (value != null) {
            return value;
        }

        // 如果本地缓存为空则读取Redis的缓存
        value = stringRedisTemplate.opsForValue().get(key);
        if (value != null) {
            // 将从 Redis 获取的值放入本地缓存
            localCacheByString.put(key, value);
        }

        return value;
    }

    /**
     * 读缓存，返回对象类型
     *
     * @param key
     * @return
     */
    public Object getObjectCache(String key) {

        // 先从本地读取缓存
        Object object = localCacheByObject.getIfPresent(key);
        if (object != null) {
            return object;
        }

        // 如果本地缓存为空则读取Redis的缓存
        object = redisTemplate.opsForValue().get(key);
        if (object != null) {
            // 将从 Redis 获取的值放入本地缓存
            localCacheByObject.put(key, object);
        }

        return object;
    }


    /**
     * 移除缓存
     *
     * @param key
     */
    public void delete(String key) {
        // 先移除本地缓存
        localCacheByString.invalidate(key);
        localCacheByObject.invalidate(key);
        // 再移除Redis缓存
        stringRedisTemplate.delete(key);
        redisTemplate.delete(key);


    }


}
