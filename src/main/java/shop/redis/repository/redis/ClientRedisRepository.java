package shop.redis.repository.redis;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import shop.redis.configuration.Configuration;
import shop.redis.model.Address;
import shop.redis.model.Client;

import java.util.Optional;

public class ClientRedisRepository {
    private final CacheService cacheService;
    private final JedisPool jedisPool;

    public ClientRedisRepository() {
        this.cacheService = new CacheService();
        this.jedisPool = new JedisPool(new JedisPoolConfig(), new Configuration().getProperty("redisUrl"));
//        this.jedisPool = new JedisPool(new JedisPoolConfig(), new Configuration().getPropertyHardcoded());
    }

    public void add(Client client) {
        try {
            var json = new ObjectMapper()
                    .findAndRegisterModules()
                    .writeValueAsString(client);
            var redisKey = "clientRedis:" + client.getClientType().getPesel();
            cacheService.setCache(redisKey, json);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public Optional<Client> getClientByPesel(String pesel) {
        var objectMapper = new ObjectMapper();
        objectMapper.findAndRegisterModules();
        try (var jedis = jedisPool.getResource()) {
            var redisKey = "clientRedis:" + pesel;
            var json = jedis.get(redisKey);
            return Optional.of(objectMapper.readValue(json, Client.class));
        } catch (Exception ignored) {

        }
        return Optional.empty();
    }

    public void deleteClient(Client client) {
        String redisKey = "clientRedis:" + client.getClientType().getPesel();
        new CacheService().invalidateCache(redisKey);
    }

    public void clientUpdateAddress(Client client, Address address) {
        client.setAddress(address);
        var objectMapper = new ObjectMapper();
        objectMapper.findAndRegisterModules();
        try (var jedis = jedisPool.getResource()) {
            var redisKey = "clientRedis:" + client.getClientType().getPesel();
            var clientJson = objectMapper.writeValueAsString(client);
            jedis.set(redisKey, clientJson);
        } catch (Exception e) {
            throw new RuntimeException("Error updating client in Redis", e);
        }
    }

}
