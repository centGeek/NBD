package shop.orm.repository;


public class PurchaseRepository extends AbstractCassandraRepository {

//    private final String collectionPurchases;
//    private final String collectionProducts;
//    private final String collectionClients;
//
//
//    public PurchaseRepository() {
//        this.database = AbstractCassandraRepository.getDatabase();
//        this.collectionPurchases = "purchases";
//        this.collectionProducts = "products";
//        this.collectionClients = "clients";
//    }
//
//    public PurchaseRepository(String nameOfColletion) {
//        this.database = AbstractCassandraRepository.getDatabase();
//        database.getCollection(nameOfColletion).drop();
//        this.collectionPurchases = nameOfColletion;
//        this.collectionProducts = "testStock";
//        this.collectionClients = "testClients";
//    }
//
//    protected static final Logger logger = LogManager.getLogger(PurchaseRepository.class);
//
//    public List<Purchase> getAllPurchasesByClient(Client client) {
//        Bson filter = Filters.eq("client._id", client.getId().toString());
//        List<Purchase> purchases = new ArrayList<>();
//
//        List<PurchaseMdb> purchaseMdbs = database.getCollection(collectionPurchases, PurchaseMdb.class).find(filter).into(new ArrayList<>());
//
//
//        for (PurchaseMdb purchaseMdb : purchaseMdbs) {
//            purchases.add(PurchaseMdb.purchaseMdbToPurchase(purchaseMdb));
//        }
//
//        return purchases;
//    }
//
//    public void buyAProduct(Purchase purchase, Product product) {
//        product.setPurchase(purchase);
//        product.setProductBought(true);
//    }
//
//    public void makeAPurchase(Purchase purchase) {
//        ClientSession clientSession = mongoClient.startSession();
//        //niestety nie da się z try with resources (chyba że automatycznie otwiera wtedy i zamyka tranzakcje)
//        try {
//            clientSession.startTransaction();
//
//            MongoCollection<PurchaseMdb> collection = database.getCollection(collectionPurchases, PurchaseMdb.class);
//            PurchaseMdb purchaseMdb = new PurchaseMdb(purchase);
//
//            MongoCollection<ProductMdb> productMdbMongoCollection = database.getCollection(collectionProducts, ProductMdb.class);
//            Bson update = Updates.inc("productBoughtCounter", 1);
//            for (ProductMdb productMdb : purchaseMdb.getProducts()) {
//                Bson filter = Filters.eq("_id", productMdb.getEntityId());
//                productMdbMongoCollection.updateOne(filter, update);
//            }
//
//            purchaseMdb.buyProducts();
//            collection.insertOne(purchaseMdb);
//            clientSession.commitTransaction();
//
//            for (Product product : purchase.getProducts()) {
//                buyAProduct(purchase, product);
//            }
//
//
//        } catch (MongoWriteException mongoWriteException) {
//
//            clientSession.abortTransaction();
//            throw new RuntimeException(mongoWriteException.getMessage());
//        } finally {
//            clientSession.close();
//        }
//
//    }
//
//    public void changeClientForPurchase(Purchase purchase, Client client) {
//        Bson filter = Filters.eq("_id", purchase.getId().toString());
//        Bson update = Updates.set("client", new ClientMdb(client));
//        MongoCollection<PurchaseMdb> productMdbMongoCollection = database.getCollection(collectionPurchases, PurchaseMdb.class);
//        List<PurchaseMdb> list = productMdbMongoCollection.find(filter).into(new ArrayList<>());
//        UpdateResult updateResult =  productMdbMongoCollection.updateOne(filter, update);
//
//    }
//
//
//    public void deletePurchase(Purchase purchase){
//        Bson filter = Filters.eq("_id", purchase.getId().toString());
//        MongoCollection<PurchaseMdb> productMdbMongoCollection = database.getCollection(collectionPurchases, PurchaseMdb.class);
//        productMdbMongoCollection.deleteOne(filter);
//    }
}

