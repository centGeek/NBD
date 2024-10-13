package shop.orm.menagers;

import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.CleanupMode;
import shop.orm.TestData;
import shop.orm.model.Client;
import shop.orm.model.Product;
import shop.orm.model.Purchase;

import java.math.BigDecimal;
import java.util.List;

public class PurchaseManagerTest {

    @Test
    public void purchaseWentCorrectly(){
        EntityManager entityManager = EntityManagerClassSingleton.getEntityManager();

        Client client = TestData.getClient1();
        ClientRegisterManager clientRegisterManager = new ClientRegisterManager();
        clientRegisterManager.clientRegister(client, entityManager);
        List<Client> allClients = clientRegisterManager.getAllClients(entityManager);

        PurchaseManager purchaseManager = new PurchaseManager();

        StockManager stockManager = new StockManager();
        stockManager.addProductToDatabase(entityManager, "Pawelki", BigDecimal.valueOf(2));
        stockManager.addProductToDatabase(entityManager, "Pawelki", BigDecimal.valueOf(2));
        stockManager.addProductToDatabase(entityManager, "Pawelki", BigDecimal.valueOf(2));
        stockManager.addProductToDatabase(entityManager, "Pawelki", BigDecimal.valueOf(2));
        stockManager.addProductToDatabase(entityManager, "PawelkiIJanki", BigDecimal.valueOf(1));

        List<Product> allProductsAvailable = stockManager.getAllProductsAvailable(entityManager);
        Purchase purchase = new Purchase(allClients.get(0), allProductsAvailable);
        purchaseManager.makeAPurchase(entityManager, purchase);
        Assertions.assertEquals(5,
                allProductsAvailable.size());
        List<Purchase> allPurchasesByClient = purchaseManager.getAllPurchasesByClient(entityManager, allClients.get(0).getId());
        Assertions.assertEquals(1,
                allPurchasesByClient.size());
        Assertions.assertEquals(5,
                allPurchasesByClient.get(0).getProducts().size());

    }


}