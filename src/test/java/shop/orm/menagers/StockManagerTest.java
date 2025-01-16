package shop.orm.menagers;


import org.junit.jupiter.api.*;
import shop.orm.model.Product;

import java.math.BigDecimal;
import java.util.List;


@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class StockManagerTest {

    private static StockManager stockManager;

    @BeforeAll
    public static void setUp() {
        stockManager = new StockManager(true);
    }

    @Test
    @Order(1)
    public void addingAndDeletingProductsCorrectly() {
        try (StockManager stockManager = new StockManager(false)) {

            long prevSize = stockManager.countProducts();
            Product product = TestData.getProduct2();
            for (int i = 0; i < 10; i++) {
                stockManager.addProductToDatabase(product.getProductName(), product.getPrice());
            }

            List<Product> allProductsByName = stockManager.getAllProductsByName(product.getProductName());
            Assertions.assertEquals(prevSize + 10, allProductsByName.size());
            allProductsByName = stockManager.getAllProductsByName(product.getProductName());
            Assertions.assertEquals(prevSize + 10, allProductsByName.size());

            stockManager.deleteProduct(allProductsByName.getFirst());

            Assertions.assertEquals(prevSize + 9, stockManager.getAllProductsByName(product.getProductName()).size());


        } catch (Exception e) {
            Assertions.fail(e);
        }
    }

    @Test
    @Order(2)
    public void changingProductPriceCorrectly() {
        Product product = TestData.getProduct1();

        try (StockManager stockManager = new StockManager(false)) {

            stockManager.addProductToDatabase(product);

            for (int i = 0; i < 2; i++) {
                stockManager.addProductToDatabase(product.getProductName(), product.getPrice());
            }
            stockManager.addProductToDatabase(product.getProductName() + "d", product.getPrice());

            stockManager.changeAllProductPricesByName(product.getProductName(), BigDecimal.valueOf(5));

            List<Product> allProductsAvailable = stockManager.getAllProductsByName(product.getProductName() + "d");
            for (Product prod : allProductsAvailable) {
                Assertions.assertEquals(TestData.getProduct1().getPrice(), prod.getPrice());
            }

            allProductsAvailable = stockManager.getAllProductsByName(product.getProductName());
            for (Product prod : allProductsAvailable) {
                Assertions.assertEquals(BigDecimal.valueOf(5), prod.getPrice());
            }

            stockManager.changeProductPrice(product, BigDecimal.valueOf(15));
            Product product1 = stockManager.getProductByNameAndId(product.getProductName(), product.getId());
            Assertions.assertEquals(BigDecimal.valueOf(15), product1.getPrice());


            stockManager.changeAllProductPricesByName(product.getProductName(), BigDecimal.valueOf(10));

            allProductsAvailable = stockManager.getAllProductsByName(product.getProductName());
            for (Product prod : allProductsAvailable) {
                Assertions.assertEquals(BigDecimal.valueOf(10), prod.getPrice());
            }


        } catch (Exception e) {
            Assertions.fail(e);
        }
    }


    @AfterAll
    public static void closeAll() {
        try {
            stockManager.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
