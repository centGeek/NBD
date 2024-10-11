package shop.orm;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import shop.orm.menagers.ClientRegisterManager;
import shop.orm.menagers.ProductManager;
import shop.orm.menagers.PurchaseManager;
import shop.orm.model.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        //Client Registering, Client selecting

        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("NBD-unit");
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        Client client = TestData.getClient1();
        ClientRegisterManager clientRegisterManager = new ClientRegisterManager();
        clientRegisterManager.clientRegister(client, entityManager);
        clientRegisterManager.clientRegister(client, entityManager);

        List<Client> allClients = clientRegisterManager.getAllClients(entityManager);
        System.out.println(allClients.toString());

        //Purchasing
        List<Product> products = new ArrayList<>();
        products.add(new Product("Snickers", BigDecimal.valueOf(15)));
        products.add(new Product("Mars", BigDecimal.valueOf(23)));
        Purchase purchase = new Purchase(client, products);
        PurchaseManager purchaseManager = new PurchaseManager();
        purchaseManager.makeAPurchase(entityManager, purchase);
        System.out.println(purchaseManager.getAllPurchasesByClient(entityManager, 1));
    }
}
