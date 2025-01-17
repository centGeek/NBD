package shop.orm.menagers;


import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import shop.orm.model.Client;
import shop.orm.model.Product;
import shop.orm.model.Purchase;
import shop.orm.repository.AbstractCassandraRepository;

import java.math.BigDecimal;
import java.util.List;

public class PurchaseManagerTest {

    private static PurchaseManager testPurchaseDrop;

    @BeforeAll
    public static void setUp() {
        testPurchaseDrop = new PurchaseManager(true);
        StockManager stockManager = new StockManager(true);
        ClientRegisterManager clientRegisterManager = new ClientRegisterManager(true);
        try {
            clientRegisterManager.close();
            stockManager.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void purchaseWentCorrectly() {
        Client client = TestData.getClient1();
        try (ClientRegisterManager clientRegisterManager = new ClientRegisterManager(false);
             StockManager stockManager = new StockManager(false);
             PurchaseManager purchaseManager = new PurchaseManager(false)
        ) {
            clientRegisterManager.clientRegister(client);

            stockManager.addProductToDatabase("Pawelki", BigDecimal.valueOf(2));
            stockManager.addProductToDatabase("Pawelki", BigDecimal.valueOf(2));
            stockManager.addProductToDatabase("Pawelki", BigDecimal.valueOf(2));
            stockManager.addProductToDatabase("Pawelki", BigDecimal.valueOf(2));
            stockManager.addProductToDatabase("PawelkiIJanki", BigDecimal.valueOf(1));

            List<Product> allProductsAvailable = stockManager.getAllProductsByName("Pawelki");
            allProductsAvailable.addAll(stockManager.getAllProductsByName("PawelkiIJanki"));

            Assertions.assertEquals(5,
                    allProductsAvailable.size());
            Purchase purchase = new Purchase(client, allProductsAvailable);
            purchaseManager.makeAPurchase(purchase);

            List<Purchase> allPurchasesByClient = purchaseManager.getAllPurchasesByClient(client);
            Assertions.assertEquals(1,
                    allPurchasesByClient.size());
            Assertions.assertEquals(5,
                    allPurchasesByClient.getFirst().getProducts().size());

            for (Product product : allProductsAvailable) {
                stockManager.deleteProduct(product);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void purchaseWentIncorrectly() {
        Client client1 = TestData.getClient1();
        Client client2 = TestData.getClient2();

        try (ClientRegisterManager clientRegisterManager = new ClientRegisterManager(false);
             StockManager stockManager = new StockManager(false);
             PurchaseManager purchaseManager = new PurchaseManager(false)
        ) {
            clientRegisterManager.clientRegister(client1);
            clientRegisterManager.clientRegister(client2);

            stockManager.addProductToDatabase("Pawelki", BigDecimal.valueOf(2));


            List<Product> allProductsAvailable = stockManager.getAllProductsByName("Pawelki");
            Assertions.assertEquals(1, allProductsAvailable.size());
            Purchase purchase1 = new Purchase(client1, allProductsAvailable);
            Purchase purchase2 = new Purchase(client2, allProductsAvailable);
            purchaseManager.makeAPurchase(purchase1);
            Assertions.assertThrows(Exception.class, () -> purchaseManager.makeAPurchase(purchase2));

            for (Product product : allProductsAvailable) {
                stockManager.deleteProduct(product);
            }
        } catch (Exception e) {
            Assertions.fail(e.getMessage());
        }
    }

    @Test
    public void changeClientAndDeleteTest() {
        Client client1 = TestData.getClient1();
        Client client2 = TestData.getClient2();
        try (ClientRegisterManager clientRegisterManager = new ClientRegisterManager(false);
             StockManager stockManager = new StockManager(false);
             PurchaseManager purchaseManager = new PurchaseManager(false)
        ) {

            clientRegisterManager.clientRegister(client1);
            clientRegisterManager.clientRegister(client2);
            stockManager.addProductToDatabase("Pawelki", BigDecimal.valueOf(2));
            List<Product> allProductsAvailable = stockManager.getAllProductsByName("Pawelki");
            Purchase purchase1 = new Purchase(client1, allProductsAvailable);
            purchaseManager.makeAPurchase(purchase1);

            purchaseManager.changeClientForPurchase(purchase1, client2);

            List<Purchase> purchaseList1 = purchaseManager.getAllPurchasesByClient(client1);
            Assertions.assertEquals(0, purchaseList1.size());

            List<Purchase> purchaseList = purchaseManager.getAllPurchasesByClient(client2);
            Assertions.assertEquals(1, purchaseList.size());

            purchaseManager.deletePurchase(purchase1);
            purchaseList = purchaseManager.getAllPurchasesByClient(client2);
            Assertions.assertEquals(0, purchaseList.size());

            for (Product product : allProductsAvailable) {
                stockManager.deleteProduct(product);
            }

        } catch (Exception e) {
            Assertions.fail(e.getMessage());
        }

    }

    @Test
    public void whoBoughtThisProductTest() {
        Client client1 = TestData.getClient1();
        Product product1 = TestData.getProduct1();
        try (StockManager stockManager = new StockManager(false);
             PurchaseManager purchaseManager = new PurchaseManager(false)
        ) {

            stockManager.addProductToDatabase(product1);
            List<Product> allProductsAvailable = stockManager.getAllProductsByName("Knoppers");
            Purchase purchase1 = new Purchase(client1, allProductsAvailable);

            purchaseManager.makeAPurchase(purchase1);
            Assertions.assertEquals(client1.getId(), purchaseManager.whoBoughtThisProduct(product1));

            stockManager.deleteProduct(product1);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }


    }


    @AfterAll
    public static void closeVirtualConnection() {
        try {
            testPurchaseDrop.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}