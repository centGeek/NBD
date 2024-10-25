package shop.orm.menagers;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import shop.orm.TestData;
import shop.orm.model.Product;

import java.math.BigDecimal;
import java.util.List;

public class StockManagerTest {

    @Test
    public void addingProductsCorrectly(){
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("NBD-unit");
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        StockManager stockManager = new StockManager();
        Product product = TestData.getProduct2();
        for (int i = 0; i < 10; i++) {
            stockManager.addProductToDatabase(entityManager, product.getProductName(), product.getPrice());
        }

        List<Product> allProductsByName = stockManager.getAllProductsByName(entityManager, product.getProductName());
        Assertions.assertEquals(10, allProductsByName.size());
        allProductsByName = stockManager.getAllProductsByName(entityManager, product.getProductName());
        Assertions.assertEquals(10, allProductsByName.size());
        entityManager.close();
        entityManagerFactory.close();
    }
    @Test
    public void changingProductPriceCorrectly(){
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("NBD-unit");
        EntityManager entityManager = entityManagerFactory.createEntityManager();        Product product = TestData.getProduct1();
        StockManager stockManager = new StockManager();
        for (int i = 0; i < 55; i++) {
            stockManager.addProductToDatabase(entityManager, product.getProductName(), product.getPrice());

        }
        stockManager.addProductToDatabase(entityManager, product.getProductName()+"d", product.getPrice());
        stockManager.changeProductPrice(entityManager, product.getProductName(), BigDecimal.valueOf(5));
        List<Product> allProductsAvailable = stockManager.getAllProductsByName(entityManager, product.getProductName());
        for (Product prod : allProductsAvailable) {
            Assertions.assertEquals(BigDecimal.valueOf(5), prod.getPrice());
        }
        entityManager.close();
        entityManagerFactory.close();
    }
    @AfterAll
    public static void closeAll(){
        EntityManagerClassSingleton.closeEntityManagerFactory();
        EntityManagerClassSingleton.closeEntityManager();
    }

}
