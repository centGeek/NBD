package shop.redis.decorators;

import shop.redis.model.Client;
import shop.redis.model.Purchase;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface IPurchaseRepository {
    List<Purchase> getAllPurchasesByClient(Client client);
    Optional<Purchase> getPurchaseById(UUID uuid);
    void makeAPurchase(Purchase purchase);
    void deletePurchase(Purchase purchase);
}
