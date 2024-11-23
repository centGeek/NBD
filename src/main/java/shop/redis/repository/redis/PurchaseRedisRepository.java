package shop.redis.repository.redis;

import com.fasterxml.jackson.databind.ObjectMapper;
import redis.clients.jedis.JedisPool;
import shop.redis.configuration.JedisPoolConfiguration;
import shop.redis.model.Purchase;

public class PurchaseRedisRepository {
    private final JedisPool jedisPool;
    private final ObjectMapper objectMapper;

    public PurchaseRedisRepository() {
        this.jedisPool = JedisPoolConfiguration.getPoolConfig();
        this.objectMapper = new ObjectMapper();
    }

    public void addPurchase(Purchase purchase) {
        objectMapper.findAndRegisterModules();
        try (var jedis = jedisPool.getResource()) {
            String json = objectMapper.writeValueAsString(purchase);
            jedis.set("clientRedis:" + purchase.getId(), json);
            jedis.disconnect();
        } catch (Exception ignored) {

        }
    }

    public void getPurchase(Purchase purchase) {

        try (var jedis = jedisPool.getResource()) {
            String json = objectMapper.writeValueAsString(purchase);
            jedis.set("clientRedis:" + purchase.getId(), json);
            jedis.disconnect();
        } catch (Exception ignored) {

        }
    }
}
