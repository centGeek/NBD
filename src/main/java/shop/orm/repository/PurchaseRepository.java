package shop.orm.repository;


import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.PagingIterable;
import com.datastax.oss.driver.api.core.cql.SimpleStatement;
import com.datastax.oss.driver.api.core.type.DataTypes;
import com.datastax.oss.driver.api.querybuilder.SchemaBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import shop.orm.model.Client;
import shop.orm.model.Product;
import shop.orm.model.Purchase;
import shop.orm.repository.classes.CassandraConsts;
import shop.orm.repository.classes.ProductCassandra;
import shop.orm.repository.classes.PurchaseByClientCassandra;
import shop.orm.repository.classes.PurchaseByProductCassandra;
import shop.orm.repository.dao.ProductDao;
import shop.orm.repository.dao.PurchaseByClientDao;
import shop.orm.repository.dao.PurchaseByProductDao;
import shop.orm.repository.mapper.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class PurchaseRepository extends AbstractCassandraRepository {

    protected static final Logger logger = LogManager.getLogger(PurchaseRepository.class);
    private final CqlSession session;


    public PurchaseRepository() {
        this.session = AbstractCassandraRepository.getDatabase();
        createTables();
    }

    public PurchaseRepository(boolean dropAndCreate) {
        this.session = AbstractCassandraRepository.getDatabase();
        if (dropAndCreate) {
            createTables();
        }
    }

    private void createTables() {

        SimpleStatement purchasesByClientDrop = SchemaBuilder.dropTable(CassandraConsts.PURCHASES_BY_CLIENT_TABLE_NAME).ifExists().build();
        session.execute(purchasesByClientDrop);
        SimpleStatement purchasesByProductDrop = SchemaBuilder.dropTable(CassandraConsts.PURCHASE_BY_PRODUCT_CQL).ifExists().build();
        session.execute(purchasesByProductDrop);

        SimpleStatement createPurchaseByClientTable = SchemaBuilder.createTable(CassandraConsts.PURCHASES_BY_CLIENT_TABLE_CQL).ifNotExists()
                .withPartitionKey(CassandraConsts.CLIENT_ID, DataTypes.UUID)
                .withClusteringColumn(CassandraConsts.PURCHASE_BY_CLIENT_ID_CQL, DataTypes.UUID)
                .withColumn(CassandraConsts.PURCHASE_BY_CLIENT_PRODUCTS_CQL, DataTypes.frozenListOf(DataTypes.frozenMapOf(DataTypes.TEXT, DataTypes.UUID)))
                .build();
        session.execute(createPurchaseByClientTable);

        SimpleStatement createPurchaseByProductTable = SchemaBuilder.createTable(CassandraConsts.PURCHASE_BY_PRODUCT_CQL).ifNotExists()
                .withPartitionKey(CassandraConsts.PURCHASE_BY_PRODUCT_PRODUCT_CQL, DataTypes.TEXT)
                .withClusteringColumn(CassandraConsts.PURCHASE_BY_PRODUCT_ID_CQL, DataTypes.UUID)
                .withColumn(CassandraConsts.PURCHASE_BY_CLIENT_PRODUCTS_CQL, DataTypes.UUID)
                .withColumn(CassandraConsts.PURCHASE_ID_CQL, DataTypes.UUID)
                .build();
        session.execute(createPurchaseByProductTable);
    }

    public List<Purchase> getAllPurchasesByClient(Client client) {

        UUID clientUUID = client.getId();

        PurchaseByClientMapper purchaseByClientMapper = new PurchaseByClientMapperBuilder(session).build();
        PurchaseByClientDao purchaseByClientDao = purchaseByClientMapper.purchaseByClientDao();

        PagingIterable<PurchaseByClientCassandra> purchaseByClientCassandras = purchaseByClientDao.findAllPurchasesOfClient(clientUUID);

        ArrayList<Purchase> purchases = new ArrayList<>();

        for (PurchaseByClientCassandra purchaseByClientCassandra : purchaseByClientCassandras) {
            purchases.add(purchaseByClientCassandra.toModel());
        }

        return purchases;
    }


    public void makeAPurchase(Purchase purchase) {

        for (Product product : purchase.getProducts()) {
            if (product.isProductBought()) {
                throw new IllegalArgumentException("One of the products is already sold.");
            }
        }


        PurchaseByClientMapper byClientMapper = new PurchaseByClientMapperBuilder(session).build();
        PurchaseByClientDao purchaseByClientDao = byClientMapper.purchaseByClientDao();

        PurchaseByProductMapper byProductMapper = new PurchaseByProductMapperBuilder(session).build();
        PurchaseByProductDao purchaseByProductDao = byProductMapper.purchaseByProductDao();

        PurchaseByClientCassandra purchaseByClientCassandra = new PurchaseByClientCassandra(purchase);
        purchaseByClientDao.insert(purchaseByClientCassandra);


        ProductMapper productMapper = new ProductMapperBuilder(session).build();
        ProductDao productDao = productMapper.productDao();

        for (Product product : purchase.getProducts()) {
            PurchaseByProductCassandra purchaseByProductCassandra = new PurchaseByProductCassandra(
                    product.getProductName(),
                    product.getId(),
                    purchase.getClient().getId(),
                    purchase.getId());
            purchaseByProductDao.insert(purchaseByProductCassandra);
            product.setProductBought(true);
            productDao.update(new ProductCassandra(product));
        }

    }

    public void changeClientForPurchase(Purchase purchase, Client client) {
        //w tabeli by CLient to trzeba usunać
        PurchaseByClientMapper byClientMapper = new PurchaseByClientMapperBuilder(session).build();
        PurchaseByClientDao purchaseByClientDao = byClientMapper.purchaseByClientDao();
        PurchaseByProductMapper byProductMapper = new PurchaseByProductMapperBuilder(session).build();
        PurchaseByProductDao purchaseByProductDao = byProductMapper.purchaseByProductDao();


        PurchaseByClientCassandra purchaseByClientCassandra = new PurchaseByClientCassandra(purchase);
        purchaseByClientDao.delete(purchaseByClientCassandra);

        //w tabeli by products nie bedzie potrzebny usuwania, wstawrczy jedynie update wiec zmieniamy obiekt

        purchase.setClient(client);


        //teraz dodajmy do BY CLIENT

        purchaseByClientCassandra = new PurchaseByClientCassandra(purchase);
        purchaseByClientDao.insert(purchaseByClientCassandra);

        //a teraz idziemy po kazdym zakupie i bedziemy robic update

        for (Product product : purchase.getProducts()) {
            PurchaseByProductCassandra purchaseByProductCassandra = new PurchaseByProductCassandra(
                    product.getProductName(),
                    product.getId(),
                    purchase.getClient().getId(),
                    purchase.getId());
            purchaseByProductDao.update(purchaseByProductCassandra);
        }

    }

    public UUID whoBoughtThisProduct(Product product) {
        PurchaseByProductMapper byProductMapper = new PurchaseByProductMapperBuilder(session).build();
        PurchaseByProductDao purchaseByProductDao = byProductMapper.purchaseByProductDao();

        PurchaseByProductCassandra purchaseByProductCassandra = purchaseByProductDao.selectById(product.getProductName(), product.getId());
        return purchaseByProductCassandra.getClientId();

    }

    public void deletePurchase(Purchase purchase) {
        PurchaseByClientCassandra purchaseByClientCassandra = new PurchaseByClientCassandra(purchase);
        PurchaseByClientMapper byClientMapper = new PurchaseByClientMapperBuilder(session).build();
        PurchaseByClientDao purchaseByClientDao = byClientMapper.purchaseByClientDao();
        purchaseByClientDao.delete(purchaseByClientCassandra);

        PurchaseByProductMapper byProductMapper = new PurchaseByProductMapperBuilder(session).build();
        PurchaseByProductDao purchaseByProductDao = byProductMapper.purchaseByProductDao();
        for (Product product : purchase.getProducts()) {
            PurchaseByProductCassandra purchaseByProductCassandra = new PurchaseByProductCassandra(product.getProductName(), product.getId(), purchase.getClient().getId(), purchase.getId());
            purchaseByProductDao.delete(purchaseByProductCassandra);
        }
    }
}

