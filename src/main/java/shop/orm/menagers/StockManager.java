package shop.orm.menagers;

import shop.orm.model.Product;
import shop.orm.repository.StockRepository;
import shop.orm.repository.classes.ProductCassandra;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public class StockManager implements AutoCloseable {
    private final StockRepository stockRepository;

    public StockManager() {
        this.stockRepository = new StockRepository();
    }

    public StockManager(boolean dropAndCreate) {
        this.stockRepository = new StockRepository(dropAndCreate);
    }

    public void addProductToDatabase(String productName, BigDecimal price) {
        Product product = new Product(productName, price);
        stockRepository.addProductToDatabase(product);
    }

    public void addProductToDatabase(Product product) {
        stockRepository.addProductToDatabase(product);
    }

    public void changeProductPrice(Product product, BigDecimal productPrice) {
        stockRepository.changeProductPrice(product, productPrice);
    }

    public void changeAllProductPricesByName(String productName, BigDecimal productPrice) {
        stockRepository.changeAllProductPriceByName(productName, productPrice);
    }

    public long countProducts() {
        return stockRepository.count();
    }

    public List<Product> getAllProductsByName(String productName) {
        return stockRepository.getAllProductsByName(productName);
    }

    public Product getProductByNameAndId(String productName, UUID id) {
        return stockRepository.get(productName, id);
    }


    public void deleteProduct(Product product) {
        this.stockRepository.deleteProduct(product);
    }

    @Override
    public void close() throws Exception {
        stockRepository.close();
    }
}