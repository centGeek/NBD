package shop.redis.repository.redis;

import com.fasterxml.jackson.databind.ObjectMapper;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import shop.redis.configuration.Configuration;
import shop.redis.model.Client;
import shop.redis.model.Product;
import shop.redis.model.Purchase;

import java.util.Optional;
import java.util.UUID;

public class PurchaseRedisRepository {
    private final JedisPool jedisPool;
    private final ObjectMapper objectMapper;

    public PurchaseRedisRepository() {
        this.jedisPool = new JedisPool(new JedisPoolConfig(), new Configuration().getProperty("redisUrl"));
        this.objectMapper = new ObjectMapper();
    }

    public void addPurchase(Purchase purchase) {
        objectMapper.findAndRegisterModules();
        try {
            for (Product product : purchase.getProducts()) {
                product.setProductBought(true);
            }
            var json = objectMapper.writeValueAsString(purchase);
            var redisKey = "clientRedis:" + purchase.getId();
            new CacheService().setCache(redisKey, json);
        } catch (Exception ignored) {

        }
    }

    public Optional<Purchase> getPurchaseById(UUID purchaseId) {

        try (var jedis = jedisPool.getResource()) {
            var redisKey = "clientRedis:" + purchaseId;
            jedis.get(redisKey);
            jedis.disconnect();
            var json = jedis.get(redisKey);
            return Optional.of(objectMapper.readValue(json, Purchase.class));
        } catch (Exception ignored) {

        }
        return Optional.empty();
    }
}
