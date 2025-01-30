package shop.orm.repository;

import com.mongodb.MongoWriteException;
import com.mongodb.client.ClientSession;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import com.mongodb.client.result.UpdateResult;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bson.conversions.Bson;
import shop.orm.model.Client;
import shop.orm.model.Product;
import shop.orm.model.Purchase;
import shop.orm.repository.MongoDBClasses.ClientMdb;
import shop.orm.repository.MongoDBClasses.ProductMdb;
import shop.orm.repository.MongoDBClasses.PurchaseMdb;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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
        purchase.setId(UUID.randomUUID());
        ClientSession clientSession = mongoClient.startSession();
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

    }

    public void changeClientForPurchase(Purchase purchase, Client client) {
        Bson filter = Filters.eq("_id", purchase.getId().toString());
        Bson update = Updates.set("client", new ClientMdb(client));
        MongoCollection<PurchaseMdb> productMdbMongoCollection = database.getCollection(collectionPurchases, PurchaseMdb.class);
        List<PurchaseMdb> list = productMdbMongoCollection.find(filter).into(new ArrayList<>());
        UpdateResult updateResult =  productMdbMongoCollection.updateOne(filter, update);

    }


    public List<Purchase> getAllPurchases(){
        List<Purchase> purchases = new ArrayList<>();

        List<PurchaseMdb> purchaseMdbs = database.getCollection(collectionPurchases, PurchaseMdb.class)
                .find()
                .into(new ArrayList<>());

        for (PurchaseMdb purchaseMdb : purchaseMdbs) {
            purchases.add(PurchaseMdb.purchaseMdbToPurchase(purchaseMdb));
        }

        return purchases;
    }

    public void deletePurchase(Purchase purchase){
        Bson filter = Filters.eq("_id", purchase.getId().toString());
        MongoCollection<PurchaseMdb> productMdbMongoCollection = database.getCollection(collectionPurchases, PurchaseMdb.class);
        productMdbMongoCollection.deleteOne(filter);
    }
}

