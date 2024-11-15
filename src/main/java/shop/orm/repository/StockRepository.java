package shop.orm.repository;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.*;
import com.mongodb.client.result.UpdateResult;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bson.Document;
import org.bson.conversions.Bson;
import shop.orm.model.Product;
import shop.orm.repository.MongoDBClasses.ProductMdb;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class StockRepository extends AbstractMongoRepository {

    private final MongoDatabase database;
    private final String nameOfCollection;

    public StockRepository() {
        this.database = AbstractMongoRepository.getDatabase();
        this.nameOfCollection = "products";
        ValidationOptions validationOptions = new ValidationOptions().validator(
                Document.parse("""
                        {
                            $jsonSchema: {
                                "bsonType": "object",
                                "required": [ "productName", "price", "productBoughtCounter" ],
                                "properties": {
                                    "productName": {
                                        "bsonType": "string",
                                        "minLength": 1,
                                        "maxLength": 100
                                    },
                                    "price": {
                                        "bsonType": "decimal",
                                        "minimum": 0.0,
                                        "description": "must be a positive decimal number"
                                    },
                                    "productBoughtCounter": {
                                        "bsonType": "int",
                                        "minimum": 0,
                                        "maximum": 1,
                                        "description": "must be 1 for bought and 0 for available"
                                    }
                                }
                            }
                        }
                        """)
        ).validationAction(ValidationAction.ERROR);

        CreateCollectionOptions createCollectionOptions = new CreateCollectionOptions()
                .validationOptions(validationOptions);
        database.createCollection(nameOfCollection, createCollectionOptions);
    }

    public StockRepository(String nameOfCollection) {
        this.database = AbstractMongoRepository.getDatabase();
        this.nameOfCollection = nameOfCollection;
        MongoCollection<Document> collection = database.getCollection(nameOfCollection);
        collection.drop();
        ValidationOptions validationOptions = new ValidationOptions().validator(
                Document.parse("""
                        {
                            $jsonSchema: {
                                "bsonType": "object",
                                "required": [ "productName", "price", "productBoughtCounter" ],
                                "properties": {
                                    "productName": {
                                        "bsonType": "string",
                                        "minLength": 1,
                                        "maxLength": 100
                                    },
                                    "price": {
                                        "bsonType": "decimal",
                                        "minimum": 0.0,
                                        "description": "must be a positive decimal number"
                                    },
                                    "productBoughtCounter": {
                                        "bsonType": "int",
                                        "minimum": 0,
                                        "maximum": 1,
                                        "description": "must be 1 for bought and 0 for available"
                                    }
                                }
                            }
                        }
                        """)
        ).validationAction(ValidationAction.ERROR);

        CreateCollectionOptions createCollectionOptions = new CreateCollectionOptions()
                .validationOptions(validationOptions);

        database.createCollection(nameOfCollection, createCollectionOptions);
    }

    protected static final Logger logger = LogManager.getLogger(StockRepository.class);

    public void addProductToDatabase(Product product) {
        try {

            MongoCollection<ProductMdb> collection = database.getCollection(nameOfCollection, ProductMdb.class);
            ProductMdb productMdb = new ProductMdb(product);
            collection.insertOne(productMdb);

            logger.log(Level.INFO, String.format("Product %s added to database", product));
        } catch (Exception e) {
            logger.log(Level.ERROR, "Transaction failed and rollback occurred", e);
        }
    }


    public void changeProductPrice(String productName, BigDecimal productPrice) {
        Bson filter = Filters.and(
                Filters.eq("productName", productName),
                Filters.eq("productBoughtCounter", 0));
        Bson update = Updates.set("price", productPrice);

        database.getCollection(nameOfCollection, ProductMdb.class)
                .updateMany(filter, update);


        logger.log(Level.INFO, String.format("Products: %s changed price to %s",
                productName, productPrice));
    }

    public List<Product> getAllProductsByName(String productName) {

        Bson filter = Filters.eq("productName", productName);
        ArrayList<ProductMdb> collection = database.getCollection(nameOfCollection, ProductMdb.class).find(filter).into(new ArrayList<>());
        ArrayList<Product> products = new ArrayList<>();
        for (ProductMdb productMdb : collection) {
            products.add(ProductMdb.productFromProductMdb(productMdb));
        }

        return products;
    }

    public List<Product> getAllProductsAvailable() {
        Bson filter = Filters.eq("productBoughtCounter", 0);
        ArrayList<ProductMdb> collection = database.getCollection(nameOfCollection, ProductMdb.class).find(filter).into(new ArrayList<>());
        ArrayList<Product> products = new ArrayList<>();
        for (ProductMdb productMdb : collection) {
            products.add(ProductMdb.productFromProductMdb(productMdb));
        }
        return products;
    }

    public void deleteProduct(Product product) {
        Bson filter = Filters.eq("productName", product.getProductName());
        database.getCollection(nameOfCollection, ProductMdb.class).deleteOne(filter);
    }

}
