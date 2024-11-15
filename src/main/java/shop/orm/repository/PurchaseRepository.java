package shop.orm.repository;

import com.mongodb.MongoWriteException;
import com.mongodb.client.ClientSession;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bson.conversions.Bson;
import shop.orm.model.Client;
import shop.orm.model.Product;
import shop.orm.model.Purchase;
import shop.orm.repository.MongoDBClasses.ProductMdb;
import shop.orm.repository.MongoDBClasses.PurchaseMdb;

import java.util.ArrayList;
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
        database.getCollection(nameOfColletion).drop();
        this.collectionPurchases = nameOfColletion;
        this.collectionProducts = "testStock";
        this.collectionClients = "testClients";
    }

    protected static final Logger logger = LogManager.getLogger(PurchaseRepository.class);

    public List<Purchase> getAllPurchasesByClient(Client client) {
        Bson filter = Filters.eq("client._id", client.getId().toString());
        List<Purchase> purchases = new ArrayList<>();

        List<PurchaseMdb> purchaseMdbs = database.getCollection(collectionPurchases, PurchaseMdb.class).find(filter).into(new ArrayList<>());


        for (PurchaseMdb purchaseMdb : purchaseMdbs) {
            purchases.add(PurchaseMdb.purchaseMdbToPurchase(purchaseMdb));
        }

        return purchases;
    }

    public void buyAProduct(Purchase purchase, Product product) {
        product.setPurchase(purchase);
        product.setProductBought(true);
    }

    public void makeAPurchase(Purchase purchase) {
        ClientSession clientSession = mongoClient.startSession();
        //niestety nie da się z try with resources (chyba że automatycznie otwiera wtedy i zamyka tranzakcje)
        try {
            clientSession.startTransaction();

            MongoCollection<PurchaseMdb> collection = database.getCollection(collectionPurchases, PurchaseMdb.class);
            PurchaseMdb purchaseMdb = new PurchaseMdb(purchase);

            MongoCollection<ProductMdb> productMdbMongoCollection = database.getCollection(collectionProducts, ProductMdb.class);
            Bson update = Updates.inc("productBoughtCounter", 1);
            for (ProductMdb productMdb : purchaseMdb.getProducts()) {
                Bson filter = Filters.eq("_id", productMdb.getEntityId());
                productMdbMongoCollection.updateOne(filter, update);
            }

            purchaseMdb.buyProducts();
            collection.insertOne(purchaseMdb);
            clientSession.commitTransaction();

            for (Product product : purchase.getProducts()) {
                buyAProduct(purchase, product);
            }


        } catch (MongoWriteException mongoWriteException) {

            clientSession.abortTransaction();
            throw new RuntimeException(mongoWriteException.getMessage());
        } finally {
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
