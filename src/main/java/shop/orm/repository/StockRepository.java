package shop.orm.repository;

import com.datastax.oss.driver.api.core.CqlIdentifier;
import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.PagingIterable;
import com.datastax.oss.driver.api.core.cql.SimpleStatement;
import com.datastax.oss.driver.api.core.type.DataTypes;
import com.datastax.oss.driver.api.querybuilder.SchemaBuilder;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import shop.orm.model.Product;
import shop.orm.repository.classes.CassandraConsts;
import shop.orm.repository.classes.ClientTypeConsts;
import shop.orm.repository.classes.ProductCassandra;
import shop.orm.repository.dao.ProductDao;
import shop.orm.repository.mapper.ProductMapper;
import shop.orm.repository.mapper.ProductMapperBuilder;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class StockRepository extends AbstractCassandraRepository {
    protected static final Logger logger = LogManager.getLogger(StockRepository.class);

    private final CqlSession session;

    public StockRepository() {
        this.session = AbstractCassandraRepository.getDatabase();
        createTable();
    }

    public StockRepository(boolean dropAndCreate) {
        this.session = AbstractCassandraRepository.getDatabase();
        if (dropAndCreate) {
            createTable();
        }
    }

    private void createTable() {
        //Drop and Create
        SimpleStatement dropTableClient = SchemaBuilder.dropTable(CassandraConsts.PRODUCT_TABLE_NAME_CQL).ifExists().build();
        session.execute(dropTableClient);

        //Creating Product Table
        SimpleStatement createClientTable = SchemaBuilder.createTable(CassandraConsts.PRODUCT_TABLE_NAME_CQL).ifNotExists()
                .withPartitionKey(CassandraConsts.PRODUCT_NAME_CQL, DataTypes.TEXT)
                .withClusteringColumn(CassandraConsts.PRODUCT_ID_NAME, DataTypes.UUID)
                .withColumn(CassandraConsts.PRODUCT_PRICE_CQL, DataTypes.DECIMAL)
                .withColumn(CassandraConsts.PRODUCT_BOUGHT_CQL, DataTypes.BOOLEAN)
                .build();
        session.execute(createClientTable);
    }

    public void addProductToDatabase(Product product) {
        try {
            ProductCassandra productCassandra = new ProductCassandra(product);
            ProductMapper productMapper = new ProductMapperBuilder(session).build();
            ProductDao productDao = productMapper.productDao();
            productDao.insert(productCassandra);

            logger.log(Level.INFO, String.format("Product %s added to database", product));
        } catch (Exception e) {
            logger.log(Level.ERROR, "Transaction failed and rollback occurred", e);
        }
    }


    public void changeProductPrice(Product product, BigDecimal productPrice) {
        ProductMapper productMapper = new ProductMapperBuilder(session).build();
        ProductDao productDao = productMapper.productDao();
        product.setPrice(productPrice);
        ProductCassandra productCassandra = new ProductCassandra(product);

        productDao.update(productCassandra);
        logger.log(Level.INFO, String.format("Products: %s changed price to %s",
                product.getProductName(), productPrice));
    }

    public long count() {
        ProductMapper productMapper = new ProductMapperBuilder(session).build();
        ProductDao productDao = productMapper.productDao();
        return productDao.count();
    }

    public void deleteProduct(Product product) {
        ProductCassandra productCassandra = new ProductCassandra(product);
        ProductMapper productMapper = new ProductMapperBuilder(session).build();
        ProductDao productDao = productMapper.productDao();
        productDao.delete(productCassandra);
    }


    public void changeAllProductPriceByName(String productName, BigDecimal productPrice) {
        ProductMapper productMapper = new ProductMapperBuilder(session).build();
        ProductDao productDao = productMapper.productDao();


        PagingIterable<ProductCassandra> products = productDao.findByName(productName);

        for (ProductCassandra product : products) {
            ProductCassandra updatedProduct = new ProductCassandra(
                    product.getProductName(),
                    product.getId(),
                    productPrice, // Nowa cena
                    product.isProductBought()
            );
            productDao.insert(updatedProduct);
        }
    }


    public List<Product> getAllProductsByName(String productName) {
        ProductMapper productMapper = new ProductMapperBuilder(session).build();
        ProductDao productDao = productMapper.productDao();
        List<Product> outputList = new ArrayList<Product>();
        PagingIterable<ProductCassandra> productsCassandra = productDao.findByName(productName);
        for (ProductCassandra productCassandra : productsCassandra) {
            outputList.add(productCassandra.toProductModel());
        }
        return outputList;
    }


    public Product get(String productName, UUID uuid) {
        ProductMapper productMapper = new ProductMapperBuilder(session).build();
        ProductDao productDao = productMapper.productDao();

        return productDao.findByNameAndId(productName, uuid).toProductModel();
    }
}
