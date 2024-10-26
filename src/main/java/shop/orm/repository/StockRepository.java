package shop.orm.repository;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import shop.orm.model.Product;

import java.math.BigDecimal;
import java.util.List;

public class StockRepository {
    protected static final Logger logger = LogManager.getLogger(ClientRegisterRepository.class);

    public void addProductToDatabase( String productName, BigDecimal price) {
//        try {
//            entityManager.getTransaction().begin();
//            Product product = new Product(productName, price);
//            entityManager.persist(product);
//            entityManager.getTransaction().commit();
//            logger.log(Level.INFO, String.format("Product %s added to database", product));
//        } catch (Exception e) {
//            if (entityManager.getTransaction().isActive()) {
//                entityManager.getTransaction().rollback();
//            }
//            logger.log(Level.ERROR, "Transaction failed and rollback occurred", e);
//        }
    }

    public void changeProductPrice( String product_name, BigDecimal productPrice) {
//        List<Product> allProductsByName = this.getAllProductsByName(entityManager, product_name);
//        entityManager.getTransaction().begin();
//        for (Product prod : allProductsByName) {
//            prod.setPrice(productPrice);
//        }
//        entityManager.getTransaction().commit();
//        logger.log(Level.INFO, String.format("Products: %s changed price to %s",
//                allProductsByName.get(0).getProductName(), productPrice));
    }

    public List<Product> getAllProductsByName( String productName) {
//        String selectQuery = "SELECT p FROM Product p where p.productName =:productName";
//        TypedQuery<Product> query = entityManager.createQuery(selectQuery, Product.class);
//        query.setParameter("productName", productName);
//        return query.getResultList();
        return null;
    }

    public List<Product> getAllProductsAvailable() {
//        String query = "select p from Product p where p.isProductBought = False ";
//        return (List<Product>) entityManager.createQuery(query).getResultList();
        return null;
    }
}
