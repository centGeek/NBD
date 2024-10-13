package shop.orm.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.OptimisticLockException;
import jakarta.persistence.TypedQuery;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import shop.orm.model.Product;
import shop.orm.model.Purchase;

import java.util.List;

public class PurchaseRepository {

    protected static final Logger logger = LogManager.getLogger(ClientRegisterRepository.class);

    public List<Purchase> getAllPurchasesByClient(EntityManager entityManager, long clientId) {
        String selectQuery = "SELECT p FROM Purchase p where p.client.id =: clientId";
        TypedQuery<Purchase> query = entityManager.createQuery(selectQuery, Purchase.class);
        query.setParameter("clientId", clientId);
        return query.getResultList();
    }

    public void buyAProduct(Purchase purchase, Product product) {
        product.setPurchase(purchase);
        product.setProductBought(true);
    }

    public void makeAPurchase(EntityManager entityManager, Purchase purchase) {
        List<Product> products = purchase.getProducts();
        try {
            entityManager.getTransaction().begin();
            for (Product product : products) {
                Product managedProduct = entityManager.find(Product.class, product.getId());
                this.buyAProduct(purchase, managedProduct);
            }
            entityManager.persist(purchase);
            entityManager.getTransaction().commit();
        } catch (OptimisticLockException optimisticLockException) {
            if (entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }
            logger.log(Level.ERROR, "Optimistic lock exception");
        } catch (Exception exception) {
            if (entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }
            logger.log(Level.ERROR, exception);
        }
    }
}
