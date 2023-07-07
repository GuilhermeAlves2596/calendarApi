package com.alves.calendar.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import com.alves.calendar.entities.Holiday;

@Configuration
public class RedisConfig {
    @Bean
    public RedisTemplate<String, Holiday> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, Holiday> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new Jackson2JsonRedisSerializer<>(Holiday.class));
        redisTemplate.setHashValueSerializer(new Jackson2JsonRedisSerializer<>(Holiday.class));
        redisTemplate.afterPropertiesSet();
        return redisTemplate;
    }
}

