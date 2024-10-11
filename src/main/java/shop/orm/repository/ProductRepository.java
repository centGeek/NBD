package shop.orm.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import shop.orm.model.Client;
import shop.orm.model.ClientType;
import shop.orm.model.Product;
import shop.orm.model.Purchase;

import java.math.BigDecimal;
import java.util.List;

public class ProductRepository {

    public void addProductToDatabase(EntityManager entityManager, String productName, BigDecimal price) {
        entityManager.getTransaction().begin();
        Product product = new Product(productName, price);
        entityManager.persist(product);
        entityManager.getTransaction().commit();
    }
    public void changeProductPrice(EntityManager entityManager, Product product, BigDecimal productPrice) {
        List<Product> allProductsByName = this.getAllProductsByName(entityManager, product.getProductName());
        entityManager.getTransaction().begin();
        for (Product prod : allProductsByName) {
            prod.setPrice(productPrice);
        }
        entityManager.getTransaction().commit();
    }
    public List<Product> getAllProductsByName(EntityManager entityManager, String productName){
        String selectQuery = "SELECT p FROM Product p where p.productName =:productName";
        TypedQuery<Product> query = entityManager.createQuery(selectQuery, Product.class);
        query.setParameter("productName", productName);
        return query.getResultList();
    }
}
