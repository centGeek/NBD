package shop.orm.menagers;

import jakarta.persistence.EntityManager;
import shop.orm.model.Product;
import shop.orm.repository.ProductRepository;

import java.math.BigDecimal;
import java.util.List;

public class ProductManager {
    private final ProductRepository productRepository;

    public ProductManager() {
        this.productRepository = new ProductRepository();
    }

    public void addProductToDatabase(EntityManager entityManager, String productName, BigDecimal price) {
        productRepository.addProductToDatabase(entityManager, productName, price);
    }

    public void changeProductPrice(EntityManager entityManager, Product product, BigDecimal productPrice) {
        productRepository.changeProductPrice(entityManager, product, productPrice);
    }
    public List<Product> getAllProductsByName(EntityManager entityManager, String productName) {
        return productRepository.getAllProductsByName(entityManager, productName);
    }
}