package shop.redis.repository.redis;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import redis.clients.jedis.JedisCluster;
import shop.redis.configuration.DatabaseConfiguration;
import shop.redis.model.Client;
import shop.redis.repository.entity.redisEntities.ClientRedis;

public class ClientRedisRepository {
    private final JedisCluster jedisCluster;

    public ClientRedisRepository() {
        this.jedisCluster = DatabaseConfiguration.getJedisCluster();
    }

    public void add(Client client) {
        var objectMapper = new ObjectMapper();
        try {
            var clientRedis = new ClientRedis(client);
            var json = objectMapper.writeValueAsString(clientRedis);
            String redisKey = "clientRedis:" + clientRedis.getId();
            jedisCluster.set(redisKey, json);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
    public ClientRedis getClient(String clientId) {
        try {
            var redisKey = "clientRedis:" + clientId;
            var json = jedisCluster.get(redisKey);
            if (json != null) {
                var objectMapper = new ObjectMapper();
                return objectMapper.readValue(json, ClientRedis.class);
            } else {
                return null;
            }
        } catch (Exception e) {
            throw new RuntimeException("Error retrieving client from Redis", e);
        }
    }
    public void deleteClient(Client client) {
        var redisKey = "clientRedis:" + client.getId();
        jedisCluster.del(redisKey);
    }
}
