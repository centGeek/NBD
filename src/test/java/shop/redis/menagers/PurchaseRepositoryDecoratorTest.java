package shop.redis.menagers;

import com.redis.testcontainers.RedisContainer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testcontainers.utility.DockerImageName;
import shop.redis.decorators.PurchaseRepositoryDecorator;
import shop.redis.model.Purchase;
import shop.redis.repository.mongoDb.ClientMongoRegisterRepository;
import shop.redis.repository.redis.CacheService;

import java.util.Collections;
import java.util.List;

public class PurchaseRepositoryDecoratorTest {
    private final PurchaseRepositoryDecorator purchaseDecorator = new PurchaseRepositoryDecorator();

    @BeforeAll
    static void setUp() {
        var redis = new RedisContainer(DockerImageName.parse("redis:6.2.6"));
        redis.start();
    }


    @Test
    void thatPurchaseIsAddedCorrectly() {
        var expectedPurchase = TestData.getPurchase();
        purchaseDecorator.makeAPurchase(expectedPurchase);
        purchaseDecorator.getPurchaseById(expectedPurchase.getId());

        var actualPurchaseById = purchaseDecorator.getPurchaseById(expectedPurchase.getId()).get();

        Assertions.assertEquals(expectedPurchase.getProducts().getFirst().getProductName(),
                actualPurchaseById.getProducts().getFirst().getProductName());
    }
    @Test
    void thatPurchaseIsDeletedCorrectly(){
        var expectedPurchase = TestData.getPurchase();

        purchaseDecorator.makeAPurchase(expectedPurchase);
        purchaseDecorator.deletePurchase(expectedPurchase);

        List<Purchase> allPurchasesByClient = purchaseDecorator.getAllPurchasesByClient(expectedPurchase.getClient());

        Assertions.assertEquals(Collections.emptyList(), allPurchasesByClient);

    }


}
