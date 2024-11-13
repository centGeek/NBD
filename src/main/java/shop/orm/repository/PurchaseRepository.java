package shop.orm.repository;

import com.mongodb.MongoCommandException;
import com.mongodb.client.ClientSession;
import com.mongodb.client.MongoDatabase;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import shop.orm.model.Product;
import shop.orm.model.Purchase;

import java.util.List;

public class PurchaseRepository extends AbstractMongoRepository {

    private final MongoDatabase database;
    private final String collectionPurchases;
    private final String collectionProducts;
    private final String collectionClients;


    public PurchaseRepository() {
        this.database = AbstractMongoRepository.getDatabase();
        this.collectionPurchases = "purchases";
        this.collectionProducts = "products";
        this.collectionClients = "clients";
    }

    public PurchaseRepository(String nameOfColletion) {
        this.database = AbstractMongoRepository.getDatabase();
        this.collectionPurchases = nameOfColletion;
        this.collectionProducts = "testProducts";
        this.collectionClients = "testClients";
    }

    protected static final Logger logger = LogManager.getLogger(PurchaseRepository.class);

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
        ClientSession clientSession = mongoClient.startSession();
        //niestety nie da się z try with resources (chyba że automatycznie otwiera wtedy i zamyka tranzakcje)
        try{
            clientSession.startTransaction();


        } catch (MongoCommandException mongoCommandException) {
            clientSession.abortTransaction();
            throw new RuntimeException(mongoCommandException.getMessage());
        }
        finally {
            clientSession.close();
        }

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
