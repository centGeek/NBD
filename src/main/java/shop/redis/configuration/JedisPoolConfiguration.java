package shop.redis.configuration;

import redis.clients.jedis.DefaultJedisClientConfig;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class JedisPoolConfiguration {

    static {
        initConfiguration();
    }

    private static JedisPool POOL;

    public static void initConfiguration() {
        var jedisClientConfig = DefaultJedisClientConfig.builder().build();
        var poolConfig = new JedisPoolConfig();
        poolConfig.setMaxWaitMillis(50);
        POOL = new JedisPool(poolConfig, new HostAndPort("localhost", 6379), jedisClientConfig);
    }

    public static JedisPool getPoolConfig() {
        return POOL;
    }
}
