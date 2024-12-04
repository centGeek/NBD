package shop.redis.repository.redis;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import shop.redis.configuration.Configuration;

public class CacheService {

    private final JedisPool jedisPool;

    public CacheService() {
        this.jedisPool = new JedisPool(new JedisPoolConfig(), new Configuration().getProperty("redisUrl"));
    }

    public void setCache(String redisKey, String json) {
        try (Jedis jedis = jedisPool.getResource()) {
            jedis.set(redisKey, json);
            jedis.expire(redisKey, 3600);
            jedis.disconnect();
        } catch (Exception ignored) {
        }
    }

    public void flushAll() {
        try (Jedis jedis = jedisPool.getResource()) {
            jedis.flushAll();
        }
    }

    public void invalidateCache(String redisKey) {
        try (Jedis jedis = jedisPool.getResource()) {
            jedis.del(redisKey);
        } catch (Exception ignored) {
        }
    }
}
