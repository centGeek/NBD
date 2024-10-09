package shop.orm.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import shop.orm.model.Client;
import shop.orm.model.Product;
import shop.orm.model.Purchase;

import java.math.BigDecimal;
import java.util.List;

public class ProductRepository {

    public void addProductToDatabase(EntityManager entityManager, String product_name, BigDecimal price) {
        entityManager.getTransaction().begin();
        Product product = Product.builder()
                .product_name(product_name)
                .price(price)
                .build();
        entityManager.persist(product);
        entityManager.getTransaction().commit();
    }
    public void changeProductPrice(EntityManager entityManager, Product product, BigDecimal product_price) {
        entityManager.getTransaction().begin();
        product.setPrice(product_price);
        entityManager.getTransaction().commit();
    }
}
