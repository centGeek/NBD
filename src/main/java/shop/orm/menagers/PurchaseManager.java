package shop.orm.menagers;

import shop.orm.model.Client;
import shop.orm.model.Product;
import shop.orm.model.Purchase;
import shop.orm.repository.PurchaseRepository;

import java.util.List;
import java.util.UUID;

public class PurchaseManager implements AutoCloseable {
    private final PurchaseRepository purchaseRepository;

    public PurchaseManager() {
        this.purchaseRepository = new PurchaseRepository();
    }

    public PurchaseManager(boolean dropAndCreate) {
        this.purchaseRepository = new PurchaseRepository(dropAndCreate);
    }

    public List<Purchase> getAllPurchasesByClient(Client client) {
        return purchaseRepository.getAllPurchasesByClient(client);
    }

    public void makeAPurchase(Purchase purchase) {
        purchaseRepository.makeAPurchase(purchase);
    }


    public UUID whoBoughtThisProduct(Product product) {
        return purchaseRepository.whoBoughtThisProduct(product);
    }

    public void changeClientForPurchase(Purchase purchase, Client client) {
        this.purchaseRepository.changeClientForPurchase(purchase, client);
    }

    public void deletePurchase(Purchase purchase) {
        this.purchaseRepository.deletePurchase(purchase);
    }

    @Override
    public void close() throws Exception {
        purchaseRepository.close();
    }
}
