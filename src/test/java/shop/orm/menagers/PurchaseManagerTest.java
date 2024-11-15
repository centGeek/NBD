package shop.orm.menagers;


import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import shop.orm.menagers.TestData;
import shop.orm.model.Client;
import shop.orm.model.Product;
import shop.orm.model.Purchase;
import shop.orm.repository.AbstractMongoRepository;

import java.math.BigDecimal;
import java.util.List;

public class PurchaseManagerTest {


    @BeforeAll
    public static void setUp() {
        AbstractMongoRepository.getDatabase();
    }

    @Test
    public void purchaseWentCorrectly() {
        Client client = TestData.getClient1();
        try (ClientRegisterManager clientRegisterManager = new ClientRegisterManager("testClients");
             StockManager stockManager = new StockManager("testStock");
             PurchaseManager purchaseManager = new PurchaseManager("testPurchase");
        ) {
            clientRegisterManager.clientRegister(client);
            List<Client> allClients = clientRegisterManager.getAllClients();


            stockManager.addProductToDatabase("Pawelki", BigDecimal.valueOf(2));
            stockManager.addProductToDatabase("Pawelki", BigDecimal.valueOf(2));
            stockManager.addProductToDatabase("Pawelki", BigDecimal.valueOf(2));
            stockManager.addProductToDatabase("Pawelki", BigDecimal.valueOf(2));
            stockManager.addProductToDatabase("PawelkiIJanki", BigDecimal.valueOf(1));

            List<Product> allProductsAvailable = stockManager.getAllProductsAvailable();
            Assertions.assertEquals(5,
                    allProductsAvailable.size());
            Purchase purchase = new Purchase(client, allProductsAvailable);
            purchaseManager.makeAPurchase(purchase);
            List<Purchase> allPurchasesByClient = purchaseManager.getAllPurchasesByClient(client);
            Assertions.assertEquals(1,
                    allPurchasesByClient.size());
            Assertions.assertEquals(5,
                    allPurchasesByClient.get(0).getProducts().size());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void purchaseWentIncorrectly() {
        Client client1 = TestData.getClient1();
        Client client2 = TestData.getClient2();

        try (ClientRegisterManager clientRegisterManager = new ClientRegisterManager("testClients");
             StockManager stockManager = new StockManager("testStock");
             PurchaseManager purchaseManager = new PurchaseManager("testPurchase");
        ) {
            clientRegisterManager.clientRegister(client1);
            clientRegisterManager.clientRegister(client2);

            stockManager.addProductToDatabase("Pawelki", BigDecimal.valueOf(2));


            List<Product> allProductsAvailable = stockManager.getAllProductsAvailable();
            Assertions.assertEquals(1, allProductsAvailable.size());
            Purchase purchase1 = new Purchase(client1, allProductsAvailable);
            Purchase purchase2 = new Purchase(client2, allProductsAvailable);
            purchaseManager.makeAPurchase(purchase1);
            Assertions.assertThrows(Exception.class, () -> purchaseManager.makeAPurchase(purchase2));

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    @AfterAll
    public static void closeVirtualConnection() {
        AbstractMongoRepository.decrementCounter();
    }
}