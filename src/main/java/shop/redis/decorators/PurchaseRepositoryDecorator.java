package shop.redis.decorators;

import shop.redis.model.Client;
import shop.redis.model.Purchase;
import shop.redis.repository.mongoDb.PurchaseMongoRepository;
import shop.redis.repository.redis.PurchaseRedisRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class PurchaseRepositoryDecorator implements IPurchaseRepository, AutoCloseable {
    private final PurchaseMongoRepository purchaseMongoRepository;
    private final PurchaseRedisRepository purchaseRedisRepository;

    public PurchaseRepositoryDecorator() {
        this.purchaseMongoRepository = new PurchaseMongoRepository();
        this.purchaseRedisRepository = new PurchaseRedisRepository();
    }

    public PurchaseRepositoryDecorator(String nameOfCollection) {
        this.purchaseMongoRepository = new PurchaseMongoRepository(nameOfCollection);
        this.purchaseRedisRepository = new PurchaseRedisRepository();
    }

    public List<Purchase> getAllPurchasesByClient(Client client) {
        var allPurchasesByClient = purchaseRedisRepository.getAllPurchasesByClient(client);
        if (allPurchasesByClient.isEmpty()) {
            return purchaseMongoRepository.getAllPurchasesByClient(client);
        }
        return allPurchasesByClient;
    }

    public Optional<Purchase> getPurchaseById(UUID uuid) {
        return Optional.ofNullable(purchaseRedisRepository.getPurchaseById(uuid)
                .orElseGet(() -> purchaseMongoRepository.getPurchaseById(uuid)));
    }

    public void makeAPurchase(Purchase purchase) {
        purchaseRedisRepository.addPurchase(purchase);
        purchaseMongoRepository.makeAPurchase(purchase);
    }


    public void deletePurchase(Purchase purchase) {
        this.purchaseRedisRepository.deletePurchase(purchase);
        this.purchaseMongoRepository.deletePurchase(purchase);
    }

    @Override
    public void close() throws Exception {
        purchaseMongoRepository.close();
    }
}
