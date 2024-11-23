package shop.redis.configuration;

import redis.clients.jedis.DefaultJedisClientConfig;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;

import java.util.Set;

public class DatabaseConfiguration {

    private void initDbConnection() {
        var clientConfig = DefaultJedisClientConfig.builder().password("master123").build();
        var clusterUris = Set.of(
                new HostAndPort("localhost", 7001),
                new HostAndPort("localhost", 7002),
                new HostAndPort("localhost", 7003),
                new HostAndPort("localhost", 7004),
                new HostAndPort("localhost", 7005),
                new HostAndPort("localhost", 7006)
        );
        try (var jedis = new Jedis(clusterUris.iterator().next(), clientConfig)) {
            jedis.ping();
            System.out.println("Connection with Redis on node " + clusterUris.iterator().next() + " is active");
        } catch (Exception e) {
            System.err.println("Error with redis connection: " + e.getMessage());
        }
    }

    public static JedisCluster getJedisCluster() {
        var clusterUris = Set.of(
                new HostAndPort("localhost", 7001),
                new HostAndPort("localhost", 7002),
                new HostAndPort("localhost", 7003),
                new HostAndPort("localhost", 7004),
                new HostAndPort("localhost", 7005),
                new HostAndPort("localhost", 7006)
        );
        return new JedisCluster(clusterUris);
    }
}
