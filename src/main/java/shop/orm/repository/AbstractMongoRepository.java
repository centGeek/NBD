package shop.orm.repository;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.MongoCredential;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import lombok.Getter;
import org.bson.UuidRepresentation;
import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.Conventions;
import org.bson.codecs.pojo.PojoCodecProvider;

import java.util.List;

public abstract class AbstractMongoRepository implements AutoCloseable {

    protected static int closedCounter = 0;

    public AbstractMongoRepository() {
        closedCounter++;
    }

    private static MongoDatabase database;
    private static MongoClient mongoClient;
    private static ConnectionString connectionString = new ConnectionString("mongodb://mongodb1:27017,mongodb2:27018,mongodb3:27019/?replicaSet=replica_set_single");
    private static MongoCredential credential = MongoCredential.createCredential("admin", "admin", "adminpassword".toCharArray());

    private static CodecRegistry pojoCodecRegistry = CodecRegistries.fromProviders(PojoCodecProvider.
            builder().
            automatic(true)
            .conventions(List.of(Conventions.ANNOTATION_CONVENTION))
            .build());

    public static MongoDatabase getDatabase() {
        if (mongoClient == null) {
            MongoClientSettings settings = MongoClientSettings.builder()
                    .credential(credential)
                    .applyConnectionString(connectionString)
                    .uuidRepresentation(UuidRepresentation.STANDARD)
                    .codecRegistry(CodecRegistries.fromRegistries(
                            MongoClientSettings.getDefaultCodecRegistry(),
                            pojoCodecRegistry
                    ))
                    .build();
            mongoClient = MongoClients.create(settings);
            database = mongoClient.getDatabase("shop");
        }
        return database;
    }

    public static void decrementCounter() {
        closedCounter--;
        if (closedCounter == 0 && mongoClient != null) {
            mongoClient.close();
            mongoClient = null;
        }
    }

    @Override
    public void close() throws Exception {
        decrementCounter();
    }

}
