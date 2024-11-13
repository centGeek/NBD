package shop.orm.menagers;


import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import shop.orm.menagers.TestData;
import shop.orm.model.Product;

import java.math.BigDecimal;
import java.util.List;

public class StockManagerTest {

    @Test
    public void addingProductsCorrectly() {
        try (StockManager stockManager = new StockManager("stockTest")) {

            int prevSize = stockManager.getAllProductsAvailable().size();
            Product product = TestData.getProduct2();
            for (int i = 0; i < 10; i++) {
                stockManager.addProductToDatabase(product.getProductName(), product.getPrice());
            }

            List<Product> allProductsByName = stockManager.getAllProductsByName(product.getProductName());
            Assertions.assertEquals(prevSize + 10, allProductsByName.size());
            allProductsByName = stockManager.getAllProductsByName(product.getProductName());
            Assertions.assertEquals(prevSize + 10, allProductsByName.size());


        } catch (Exception e) {
            Assertions.fail(e);
        }
    }

    @Test
    public void changingProductPriceCorrectly() {
        Product product = TestData.getProduct1();

        try (StockManager stockManager = new StockManager("stockTest")) {
            for (int i = 0; i < 2; i++) {
                stockManager.addProductToDatabase(product.getProductName(), product.getPrice());
            }
            stockManager.addProductToDatabase(product.getProductName() + "d", product.getPrice());

            stockManager.changeProductPrice(product.getProductName(), BigDecimal.valueOf(5));
            List<Product> allProductsAvailable = stockManager.getAllProductsByName(product.getProductName());
            for (Product prod : allProductsAvailable) {
                Assertions.assertEquals(BigDecimal.valueOf(5), prod.getPrice());
            }
        }
        catch (Exception e){
            Assertions.fail(e);
        }
    }

    @AfterAll
    public static void closeAll() {

    }

}
