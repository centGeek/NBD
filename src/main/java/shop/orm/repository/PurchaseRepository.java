package shop.orm.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import shop.orm.model.Product;
import shop.orm.model.Purchase;

import java.util.List;

public class PurchaseRepository {


    public List<Purchase> getAllPurchasesByClient(EntityManager entityManager, long clientId){
        String selectQuery = "SELECT p FROM Purchase p where p.client.id =: clientId";
        TypedQuery<Purchase> query = entityManager.createQuery(selectQuery, Purchase.class);
        query.setParameter("clientId", clientId);
        return query.getResultList();
    }
    public void buyAProduct(Purchase purchase, Product product) {
        product.setPurchase(purchase);
        product.setProductBought(true);
    }
    public void makeAPurchase(EntityManager entityManager, Purchase purchase){
        List<Product> products = purchase.getProducts();
        entityManager.getTransaction().begin();
        for (Product product : products) {
            Product managedProduct = entityManager.find(Product.class, product.getId());
            this.buyAProduct(purchase, managedProduct);
        }
        entityManager.persist(purchase);
        entityManager.getTransaction().commit();
    }
}
