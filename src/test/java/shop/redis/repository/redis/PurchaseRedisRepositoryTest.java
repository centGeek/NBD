package shop.redis.repository.redis;

import com.redis.testcontainers.RedisContainer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.testcontainers.utility.DockerImageName;
import shop.redis.menagers.TestData;
import shop.redis.model.Purchase;

class PurchaseRedisRepositoryTest {
    private final PurchaseRedisRepository purchaseRedisRepository = new PurchaseRedisRepository();

    @BeforeAll
    public static void setupContainer(){
        var redisContainer = new RedisContainer(DockerImageName.parse("redis:6.2.6"));
        redisContainer.start();
    }
    @Test
    void thatPurchaseIsAddedCorrectly(){
        var expectedPurchase = TestData.getPurchase();
        purchaseRedisRepository.addPurchase(expectedPurchase);

        Purchase actualPurchaseById = purchaseRedisRepository.getPurchaseById(expectedPurchase.getId()).get();
        Assertions.assertEquals(expectedPurchase, actualPurchaseById);
    }
    @Test
    void thatPurchaseIsAddedAndRetrievedByClientCorrectly(){
        var expectedPurchase = TestData.getPurchase();

        purchaseRedisRepository.addPurchase(expectedPurchase);
        var actualPurchase = purchaseRedisRepository.getPurchaseById(expectedPurchase.getId()).get();

        Assertions.assertEquals(expectedPurchase, actualPurchase);
    }

}