package shop.redis.redismanagers;

import shop.redis.model.Client;
import shop.redis.model.Purchase;
import shop.redis.repository.mongoDb.PurchaseMongoRepository;
import shop.redis.repository.redis.PurchaseRedisRepository;

import java.util.List;

public class PurchaseDecorator implements AutoCloseable {
    private final PurchaseMongoRepository purchaseMongoRepository;
    private final PurchaseRedisRepository purchaseRedisRepository;

    public PurchaseDecorator() {
        this.purchaseMongoRepository = new PurchaseMongoRepository();
        this.purchaseRedisRepository = new PurchaseRedisRepository();
    }

    public PurchaseDecorator(String nameOfCollection) {
        this.purchaseMongoRepository = new PurchaseMongoRepository(nameOfCollection);
        this.purchaseRedisRepository = new PurchaseRedisRepository();
    }

    public List<Purchase> getAllPurchasesByClient(Client client) {
        return purchaseMongoRepository.getAllPurchasesByClient(client);
    }

    public void makeAPurchase(Purchase purchase) {
        purchaseRedisRepository.addPurchase(purchase);
        purchaseMongoRepository.makeAPurchase(purchase);
    }


    public void changeClientForPurchase(Purchase purchase, Client client) {
        this.purchaseMongoRepository.changeClientForPurchase(purchase, client);
    }

    public void deletePurchase(Purchase purchase) {
        this.purchaseMongoRepository.deletePurchase(purchase);
    }

    @Override
    public void close() throws Exception {
        purchaseMongoRepository.close();
    }
}
