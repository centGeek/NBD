package shop.redis.menagers;

import shop.redis.model.Client;
import shop.redis.model.Purchase;
import shop.redis.repository.mongoDb.PurchaseMongoRepository;

import java.util.List;
import java.util.UUID;

public class PurchaseManager implements AutoCloseable {
    private final PurchaseMongoRepository purchaseMongoRepository;

    public PurchaseManager() {
        this.purchaseMongoRepository = new PurchaseMongoRepository();
    }

    public PurchaseManager(String nameOfCollection) {
        this.purchaseMongoRepository = new PurchaseMongoRepository(nameOfCollection);
    }

    public List<Purchase> getAllPurchasesByClient(Client client) {
        return purchaseMongoRepository.getAllPurchasesByClient(client);
    }
    public Purchase getPurchaseById(UUID id){
        return purchaseMongoRepository.getPurchaseById(id);
    }

    public void makeAPurchase(Purchase purchase) {
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
