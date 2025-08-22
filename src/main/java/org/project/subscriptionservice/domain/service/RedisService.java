package org.project.subscriptionservice.domain.service;


import org.project.subscriptionservice.bean.UserEntity;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public interface RedisService {

    void publish(String channel, String message);

    void delete(String channel);

    String getValue(String channel);

    void setValue(String channel, String value, long time, TimeUnit timeUnit);

    Map<String, String> hash(String channel);

    void putHash(String channel, String key, String value);

    String hash(String channel, String key);

    void hashDelete(String channel, String key);

    void hashDeleteAll(String channel, Set<String> key);

    void expire(String key, Long time, TimeUnit timeUnit);

    Long add(List<UserEntity> users, String key);

    Long increment(String key);

    Set<String> topPlayer(String channel, int topNumber);

    Double score(String channel, String user);

    Long rank(String channel, String userId);

    void addToZSet(String channel, String member, int score);

}
