package shop.redis.repository.redis;

import com.fasterxml.jackson.databind.ObjectMapper;
import redis.clients.jedis.JedisPool;
import shop.redis.configuration.JedisPoolConfiguration;
import shop.redis.model.Address;
import shop.redis.model.Client;

import java.util.Optional;

public class ClientRedisRepository {
    private final JedisPool jedisPool;

    public ClientRedisRepository() {
        this.jedisPool = JedisPoolConfiguration.getPoolConfig();
    }

    public void add(Client client) {
        try (var jedis = jedisPool.getResource()) {
            var json = new ObjectMapper()
                    .findAndRegisterModules()
                    .writeValueAsString(client);
            var redisKey = "clientRedis:" + client.getClientType().getPesel();
            jedis.set(redisKey, json);
            jedis.disconnect();
        } catch (Exception ignored) {

        }
    }

    public Optional<Client> getClientByPesel(String pesel) {
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
