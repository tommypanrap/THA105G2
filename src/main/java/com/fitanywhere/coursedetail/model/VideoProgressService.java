package com.fitanywhere.coursedetail.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class VideoProgressService {

    private final RedisTemplate<String, String> redisTemplate;

    @Autowired
    public VideoProgressService(@Qualifier("StringRedisTemplate") RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public void saveProgress(String uId, String cdId, double progress) {
        String key = "user:" + uId + ":progress";
        String field = "crdetail:" + cdId;
        String value = Double.toString(progress);
        redisTemplate.opsForHash().put(key, field, value);
    }

    public Double getProgress(String uId, String cdId) {
        String key = "user:" + uId + ":progress";
        String field = "crdetail:" + cdId;
        String progressStr = (String) redisTemplate.opsForHash().get(key, field);
        return progressStr != null ? Double.parseDouble(progressStr) : 0.0;
    }

}
