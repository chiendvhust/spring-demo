package com.example.demo.config.redis;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.*;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;

@Configuration

public class RedisTemplateConfig {
    @Bean
    RedisTemplate<String, Object> redisTemplate(final LettuceConnectionFactory lettuceConnectionFactory) {
        var redisTemplate = new RedisTemplate<String, Object>();
        redisTemplate.setConnectionFactory(lettuceConnectionFactory);
        redisTemplate.setKeySerializer(new GenericJackson2JsonRedisSerializer());
        redisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer());
        redisTemplate.setDefaultSerializer(new GenericJackson2JsonRedisSerializer());
        redisTemplate.setHashValueSerializer(new GenericJackson2JsonRedisSerializer());
        return redisTemplate;
    }

    @Bean
    <V> ValueOperations<String, V> valueOperations(final RedisTemplate<String, V> redisTemplate) {
        return redisTemplate.opsForValue();
    }

    @Bean
    <V> SetOperations<String, V> setOperations(final RedisTemplate<String, V> redisTemplate) {
        return redisTemplate.opsForSet();
    }

    @Bean
    <V> ZSetOperations<String, V> zSetOperations(final RedisTemplate<String, V> redisTemplate) {
        return redisTemplate.opsForZSet();
    }

    @Bean
    <V> ListOperations<String, V> listOperations(final RedisTemplate<String, V> redisTemplate) {
        return redisTemplate.opsForList();
    }

    @Bean
    <HK, V> HashOperations<String, HK, V> hashOperations(
            final RedisTemplate<String, V> redisTemplate) { //NOSONAR
        return redisTemplate.opsForHash();
    }
}
