package shop.orm.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import shop.orm.model.Purchase;

import java.util.List;

public class PurchaseRepository {
    public List<Purchase> getAllPurchasesByClient(EntityManager entityManager, int clientId){
        entityManager.getTransaction().begin();
        String selectQuery = "SELECT p FROM Purchase p where p.client.id =: clientId";
        Query query = entityManager.createQuery(selectQuery);
        List<Purchase> purchases = query.getResultList();
        entityManager.getTransaction().commit();
        return purchases;
    }
    public List<Purchase> makeAPurchase(EntityManager entityManager, Purchase purchase){
        entityManager.getTransaction().begin();

    }
}
