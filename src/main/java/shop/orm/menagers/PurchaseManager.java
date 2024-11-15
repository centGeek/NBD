package shop.orm.menagers;

import shop.orm.model.Client;
import shop.orm.model.Purchase;
import shop.orm.repository.PurchaseRepository;

import java.util.List;

public class PurchaseManager implements AutoCloseable {
    private final PurchaseRepository purchaseRepository;

    public PurchaseManager() {
        this.purchaseRepository = new PurchaseRepository();
    }
    public PurchaseManager(String nameOfCollection) {
        this.purchaseRepository = new PurchaseRepository(nameOfCollection);
    }

    public List<Purchase> getAllPurchasesByClient(Client client) {
        return purchaseRepository.getAllPurchasesByClient(client);
    }

    public void makeAPurchase(Purchase purchase) {
        purchaseRepository.makeAPurchase(purchase);
    }

    public void changeClientForPurchase(Purchase purchase, Client client){
        this.purchaseRepository.changeClientForPurchase(purchase,client);
    }

    public void deletePurchase(Purchase purchase){
        this.purchaseRepository.deletePurchase(purchase);
    }

    @Override
    public void close() throws Exception {
       purchaseRepository.close();
    }
}
