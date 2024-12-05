package shop.redis.repository.redis;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import shop.redis.configuration.Configuration;
import shop.redis.model.Client;
import shop.redis.model.Product;
import shop.redis.model.Purchase;

import java.util.*;
import java.util.stream.Collectors;

public class PurchaseRedisRepository {
    private final JedisPool jedisPool;
    private final ObjectMapper objectMapper;

    private final CacheService cacheService;

    public PurchaseRedisRepository() {
        this.jedisPool = new JedisPool(new JedisPoolConfig(), new Configuration().getProperty("redisUrl"));
        this.objectMapper = new ObjectMapper();
        this.cacheService = new CacheService();
        objectMapper.registerModule(new JavaTimeModule());
    }

    public void addPurchase(Purchase purchase) {
        objectMapper.findAndRegisterModules();
        try {
            for (Product product : purchase.getProducts()) {
                product.setProductBought(true);
            }
            var json = objectMapper.writeValueAsString(purchase);
            var redisKey = "purchaseRedis:" + purchase.getId();
            cacheService.setCache(redisKey, json);
        } catch (Exception ignored) {

        }
    }

    public Optional<Purchase> getPurchaseById(UUID purchaseId) {

        try (var jedis = jedisPool.getResource()) {
            var redisKey = "purchaseRedis:" + purchaseId;
            jedis.get(redisKey);
            jedis.disconnect();
            var json = jedis.get(redisKey);
            return Optional.of(objectMapper.readValue(json, Purchase.class));
        } catch (Exception ignored) {

        }
        return Optional.empty();
    }

    public void deletePurchase(Purchase purchase) {
        try (var jedis = jedisPool.getResource()) {
            var json = "purchaseRedis:" + purchase.getId();
            jedis.del(json);
        }
    }

    public List<Purchase> getAllPurchasesByClient(Client client) {
        try (var jedis = jedisPool.getResource()) {
            return jedis.keys("purchaseRedis:*").stream()
                    .map(jedis::get)
                    .filter(Objects::nonNull)
                    .map(this::parseJson)
                    .filter(purchase -> purchase.getClient().getId().equals(client.getId()))
                    .collect(Collectors.toList());
        }
    }

    private Purchase parseJson(String json) {
        try {
            return objectMapper.readValue(json, Purchase.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
