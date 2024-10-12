package shop.orm.menagers;

import jakarta.persistence.EntityManager;
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

    public List<Purchase> getAllPurchasesByClient(EntityManager entityManager, long clientId) {
        return purchaseRepository.getAllPurchasesByClient(entityManager, clientId);
    }

    public void makeAPurchase(EntityManager entityManager, Purchase purchase) {
        purchaseRepository.makeAPurchase(entityManager, purchase);
    }
}
