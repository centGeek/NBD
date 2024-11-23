package shop.redis.repository.redis;

import com.fasterxml.jackson.databind.ObjectMapper;
import redis.clients.jedis.JedisPool;
import shop.redis.configuration.JedisPoolConfiguration;
import shop.redis.model.Client;

import java.util.Optional;

public class ClientRedisRepository {
    private final JedisPool jedisPool;

    public ClientRedisRepository() {
        this.jedisPool = JedisPoolConfiguration.getPoolConfig();
    }

    public void add(Client client) {
        var objectMapper = new ObjectMapper();
        objectMapper.findAndRegisterModules();
        try (var jedis = jedisPool.getResource()) {
            String json = objectMapper.writeValueAsString(client);
            String redisKey = "clientRedis:" + client.getClientType().getPesel();
            jedis.set(redisKey, json);
            jedis.disconnect();
        } catch (Exception ignored) {

        }
    }

    public Optional<Client> getClient(String pesel) {
        var objectMapper = new ObjectMapper();
        objectMapper.findAndRegisterModules();
        try (var jedis = jedisPool.getResource()) {
            var redisKey = "clientRedis:" + pesel;
            var json = jedis.get(redisKey);
            return Optional.of(objectMapper.readValue(json, Client.class));
        } catch (Exception e) {
            throw new RuntimeException("Error retrieving client from Redis", e);
        }
    }

    public void deleteClient(Client client) {
        try (var jedis = jedisPool.getResource()) {
            String redisKey = "client:" + client.getClientType().getPesel();
            jedis.disconnect();
            jedis.del(redisKey);
        }
    }
}
