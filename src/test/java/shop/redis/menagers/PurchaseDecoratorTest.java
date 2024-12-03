package shop.redis.menagers;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import shop.redis.decorators.PurchaseDecorator;

public class PurchaseDecoratorTest {
    private final PurchaseDecorator purchaseDecorator = new PurchaseDecorator();
    @Test
    void thatPurchaseIsAddedCorrectly(){
        var expectedPurchase = TestData.getPurchase();
        purchaseDecorator.makeAPurchase(expectedPurchase);
        purchaseDecorator.getPurchaseById(expectedPurchase.getId());

        var actualPurchaseById = purchaseDecorator.getPurchaseById(expectedPurchase.getId()).get();

        Assertions.assertEquals(expectedPurchase.getProducts().getFirst().getProductName(),
                actualPurchaseById.getProducts().getFirst().getProductName());
    }


}
