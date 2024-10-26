package shop.orm.repository;

import com.mongodb.ConnectionString;
import com.mongodb.MongoCredential;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoDatabase;

public abstract class AbstractMongoRepository implements AutoCloseable{

    private ConnectionString connectionString;
    private MongoCredential credential = MongoCredential.createCredential("admin","admin", "adminpassword".toCharArray());
    private MongoDatabase database;
    private MongoClient mongoClient;
}
