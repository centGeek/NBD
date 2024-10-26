package shop.orm.repository;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import shop.orm.model.Product;
import shop.orm.model.Purchase;

import java.util.List;

public class PurchaseRepository {

    protected static final Logger logger = LogManager.getLogger(ClientRegisterRepository.class);

    public List<Purchase> getAllPurchasesByClient(long clientId) {
//        String selectQuery = "SELECT p FROM Purchase p where p.client.id =: clientId";
//        TypedQuery<Purchase> query = entityManager.createQuery(selectQuery, Purchase.class);
//        query.setParameter("clientId", clientId);
//        return query.getResultList();
    return null;
    }

    public void buyAProduct(Purchase purchase, Product product) {
        product.setPurchase(purchase);
        product.setProductBought(true);
    }

    public void makeAPurchase(Purchase purchase) {
//        List<Product> products = purchase.getProducts();
//        try {
//            entityManager.getTransaction().begin();
//            for (Product product : products) {
//                Product managedProduct = entityManager.find(Product.class, product.getId());
//                this.buyAProduct(purchase, managedProduct);
//            }
//            entityManager.persist(purchase);
//            entityManager.getTransaction().commit();
//        } catch (OptimisticLockException optimisticLockException) {
//            if (entityManager.getTransaction().isActive()) {
//                entityManager.getTransaction().rollback();
//            }
//            logger.log(Level.ERROR, "Optimistic lock exception");
//        } catch (Exception exception) {
//            if (entityManager.getTransaction().isActive()) {
//                entityManager.getTransaction().rollback();
//            }
//            logger.log(Level.ERROR, exception);
//        }
    }
}
