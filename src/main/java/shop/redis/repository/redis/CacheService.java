package shop.redis.repository.redis;

import redis.clients.jedis.Jedis;
import shop.redis.configuration.JedisPoolConfiguration;

public class CacheService {

    private final Jedis jedis;

    public CacheService() {
        this.jedis = JedisPoolConfiguration.getPoolConfig().getResource();
    }

    public void setCache(String redisKey, String json) {
        try {
            jedis.set(redisKey, json);
            jedis.expire(redisKey, 3600);
            jedis.disconnect();
        } catch (Exception ignored) {
        }
    }

    public void invalidateCache(String redisKey) {
        try {
            jedis.del(redisKey);
        } catch (Exception ignored) {
        }
    }
}
