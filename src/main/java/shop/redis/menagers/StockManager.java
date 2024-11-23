package shop.redis.menagers;

import shop.redis.model.Product;
import shop.redis.repository.mongoDb.StockMongoRepository;

import java.math.BigDecimal;
import java.util.List;

public class StockManager implements AutoCloseable {
    private final StockMongoRepository stockMongoRepository;

    public StockManager() {
        this.stockMongoRepository = new StockMongoRepository();
    }

    public StockManager(String nameOfCollection) {
        this.stockMongoRepository = new StockMongoRepository(nameOfCollection);
    }

    public void addProductToDatabase(String productName, BigDecimal price) {
        Product product = new Product(productName, price);
        stockMongoRepository.addProductToDatabase(product);
    }

    public void addProductToDatabase(Product product) {
        stockMongoRepository.addProductToDatabase(product);
    }

    public void changeProductPrice(String product_name, BigDecimal productPrice) {
        stockMongoRepository.changeProductPrice(product_name, productPrice);
    }

    public List<Product> getAllProductsByName(String productName) {
        return stockMongoRepository.getAllProductsByName(productName);
    }

    public List<Product> getAllProductsAvailable() {
        return stockMongoRepository.getAllProductsAvailable();
    }

    public void deleteProduct(Product product) {
        this.stockMongoRepository.deleteProduct(product);
    }

    @Override
    public void close() throws Exception {
        stockMongoRepository.close();
    }
}