package shop.orm.menagers;

import jakarta.persistence.EntityManager;
import shop.orm.model.Product;
import shop.orm.repository.StockRepository;

import java.math.BigDecimal;
import java.util.List;

public class StockManager {
    private final StockRepository stockRepository;

    public StockManager() {
        this.stockRepository = new StockRepository();
    }

    public void addProductToDatabase(EntityManager entityManager, String productName, BigDecimal price) {
        stockRepository.addProductToDatabase(entityManager, productName, price);
    }

    public void changeProductPrice(EntityManager entityManager, Product product, BigDecimal productPrice) {
        stockRepository.changeProductPrice(entityManager, product, productPrice);
    }

    public List<Product> getAllProductsByName(EntityManager entityManager, String productName) {
        return stockRepository.getAllProductsByName(entityManager, productName);
    }

    public List<Product> getAllProductsAvailable(EntityManager entityManager) {
        return stockRepository.getAllProductsAvailable(entityManager);
    }
}