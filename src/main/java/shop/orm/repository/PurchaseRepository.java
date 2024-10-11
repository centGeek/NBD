package shop.orm.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import shop.orm.menagers.ProductManager;
import shop.orm.model.ClientType;
import shop.orm.model.Product;
import shop.orm.model.Purchase;

import java.util.List;

public class PurchaseRepository {


    public List<Purchase> getAllPurchasesByClient(EntityManager entityManager, int clientId){
        String selectQuery = "SELECT p FROM Purchase p where p.client.id =: clientId";
        TypedQuery<Purchase> query = entityManager.createQuery(selectQuery, Purchase.class);
        query.setParameter("clientId", clientId);
        return query.getResultList();
    }
    public void buyAProduct(EntityManager entityManager, Product product){
        entityManager.persist(product);
    }
    public void makeAPurchase(EntityManager entityManager, Purchase purchase){
        List<Product> products = purchase.getProducts();
        entityManager.getTransaction().begin();
        for (Product product : products) {
            this.buyAProduct(entityManager, product);
        }
        entityManager.persist(purchase);
    }
}
