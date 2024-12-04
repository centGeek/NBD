package shop.redis.benchmark;

import org.openjdk.jmh.annotations.*;
import shop.redis.decorators.ClientRepositoryDecorator;
import shop.redis.decorators.PurchaseRepositoryDecorator;
import shop.redis.menagers.ClientManager;
import shop.redis.menagers.PurchaseManager;
import shop.redis.repository.redis.ClientRedisRepository;
import shop.redis.repository.redis.PurchaseRedisRepository;

import java.util.List;
import java.util.concurrent.TimeUnit;

@State(Scope.Thread)
public class PerformanceTest {

    private final ClientRepositoryDecorator clientRepositoryDecorator = new ClientRepositoryDecorator();
    private final ClientRedisRepository clientRedisRepository = new ClientRedisRepository();
    private final ClientManager clientManager = new ClientManager();

    @Setup
    public void setUp() {
        var clients = List.of(
                PerformanceFixtures.getClient1(),
                PerformanceFixtures.getClient2(),
                PerformanceFixtures.getClient3(),
                PerformanceFixtures.getClient4(),
                PerformanceFixtures.getClient5());
        clients.forEach(client -> {
            if (!clientRepositoryDecorator.isClientRegistered(client)) {
                clientRepositoryDecorator.clientRegister(client);
            }
        });
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    public void clientRedisTest() {
        clientRedisRepository.getClientByPesel(PerformanceFixtures.getClient1().getClientType().getPesel());
    }
    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    public void clientMongoAndRedisTest() {
        clientRepositoryDecorator.getClientByPesel(PerformanceFixtures.getClient1().getClientType().getPesel());
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    public void clientMongoTest() {
        clientManager.getClientByPesel(PerformanceFixtures.getClient1().getClientType().getPesel());
    }
}
