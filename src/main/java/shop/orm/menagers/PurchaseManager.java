package shop.orm.menagers;

import shop.orm.model.Product;
import shop.orm.model.Purchase;
import shop.orm.repository.PurchaseRepository;

import java.math.BigDecimal;
import java.util.List;

public class PurchaseManager {
    private final PurchaseRepository purchaseRepository;

    public PurchaseManager() {
        this.purchaseRepository = new PurchaseRepository();
    }

    public List<Purchase> getAllPurchasesByClient(long clientId) {
        return purchaseRepository.getAllPurchasesByClient(clientId);
    }

    public void makeAPurchase(Purchase purchase) {
        purchaseRepository.makeAPurchase(purchase);
    }
}
