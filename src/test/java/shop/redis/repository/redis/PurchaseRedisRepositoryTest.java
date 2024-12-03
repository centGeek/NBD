package shop.redis.repository.redis;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import shop.redis.menagers.TestData;
import shop.redis.model.Purchase;

class PurchaseRedisRepositoryTest {
    private final PurchaseRedisRepository purchaseRedisRepository = new PurchaseRedisRepository();
    @Test
    void thatPurchaseIsAddedCorrectly(){
        var expectedPurchase = TestData.getPurchase();
        purchaseRedisRepository.addPurchase(expectedPurchase);

        Purchase actualPurchaseById = purchaseRedisRepository.getPurchaseById(expectedPurchase.getId()).get();

        Assertions.assertEquals(expectedPurchase, actualPurchaseById);
    }

}