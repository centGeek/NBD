package shop.orm.menagers;

import shop.orm.model.Product;
import shop.orm.repository.StockRepository;

import java.math.BigDecimal;
import java.util.List;

public class StockManager {
    private final StockRepository stockRepository;

    public StockManager() {
        this.stockRepository = new StockRepository();
    }

    public void addProductToDatabase(String productName, BigDecimal price) {
        stockRepository.addProductToDatabase(productName, price);
    }

    public void changeProductPrice(String product_name, BigDecimal productPrice) {
        stockRepository.changeProductPrice(product_name, productPrice);
    }

    public List<Product> getAllProductsByName(String productName) {
        return stockRepository.getAllProductsByName(productName);
    }

    public List<Product> getAllProductsAvailable() {
        return stockRepository.getAllProductsAvailable();
    }
}