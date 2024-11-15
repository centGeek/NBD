package shop.orm.menagers;

import shop.orm.model.Product;
import shop.orm.repository.StockRepository;

import java.math.BigDecimal;
import java.util.List;

public class StockManager implements AutoCloseable {
    private final StockRepository stockRepository;

    public StockManager() {
        this.stockRepository = new StockRepository();
    }

    public StockManager(String nameOfCollection) {
        this.stockRepository = new StockRepository(nameOfCollection);
    }

    public void addProductToDatabase(String productName, BigDecimal price) {
        Product product = new Product(productName, price);
        stockRepository.addProductToDatabase(product);
    }

    public void addProductToDatabase(Product product) {
        stockRepository.addProductToDatabase(product);
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

    public void deleteProduct(Product product) {
        this.stockRepository.deleteProduct(product);
    }

    @Override
    public void close() throws Exception {
        stockRepository.close();
    }
}