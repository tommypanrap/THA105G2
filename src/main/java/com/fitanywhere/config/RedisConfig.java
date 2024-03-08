package com.fitanywhere.config;

import com.fitanywhere.order.model.CartItemVO;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisConfig {

    @Bean
    public RedisTemplate<String, CartItemVO> redisTemplate(RedisConnectionFactory redisConnectionFactory){
        RedisTemplate<String,CartItemVO> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory);


        // Key 的序列化
        redisTemplate.setKeySerializer(new StringRedisSerializer());

        // Value 的序列化
        redisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer());

        // hash Key
//        redisTemplate.setHashKeySerializer(new StringRedisSerializer());class

        // hash Value
//        redisTemplate.setHashValueSerializer(new Jackson2JsonRedisSerializer<>(Object.));

        redisTemplate.afterPropertiesSet();
        return redisTemplate;
    }
}
