package shop.redis.menagers;

import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import shop.redis.model.Address;
import shop.redis.model.Client;
import shop.redis.repository.mongoDb.AbstractMongoRepository;
import shop.redis.repository.mongoEntity.ClientMdb;
import shop.redis.repository.mongoEntity.ClientTypeMdb;

import java.util.ArrayList;

public class ClientRegisterManagerTest {

    @BeforeAll
    public static void setUp() {
        AbstractMongoRepository.getDatabase();
    }

    private final String testClientCollection = "testClients";

    @Test
    public void addingTestCorrectly() {
        try (ClientRegisterManager clientRegisterManager = new ClientRegisterManager(testClientCollection)) {
            Client client = TestData.getClient1();
            clientRegisterManager.clientRegister(client);
            Assertions.assertEquals(1, clientRegisterManager.getAllClients().size());

            Assertions.assertThrows(RuntimeException.class, () -> clientRegisterManager.clientRegister(client));

            Assertions.assertEquals(1, clientRegisterManager.getAllClients().size());

            Client client2 = TestData.getClient3();
            clientRegisterManager.clientRegister(client2);
            //System.out.println(clientRegisterManager.getAllClients());

            Assertions.assertEquals(2, clientRegisterManager.getAllClients().size());
        } catch (Exception e) {
            Assertions.fail(e.getMessage());
        }
    }

    @Test
    public void randomEntityTest() {
        MongoDatabase mongoDatabase = AbstractMongoRepository.getDatabase();
        mongoDatabase.getCollection("testCollection").drop();
        TestEntity testEntity = new TestEntity("testData check");
        mongoDatabase.getCollection("testCollection", TestEntity.class).insertOne(testEntity);
        ArrayList<TestEntity> testCollection = mongoDatabase.getCollection("testCollection", TestEntity.class).find().into(new ArrayList<>());
        Assertions.assertEquals(testEntity.getData() + testEntity.getEntityId(), testCollection.getFirst().getData() + testCollection.getFirst().getEntityId());
        AbstractMongoRepository.decrementCounter();
    }

    @Test
    public void myMongoClientAddTest() {
        MongoDatabase mongoDatabase = AbstractMongoRepository.getDatabase();
        mongoDatabase.getCollection("testCollection").drop();
        ArrayList<ClientMdb> testCollection = mongoDatabase.getCollection("testCollection", ClientMdb.class).find().into(new ArrayList<>());
        Assertions.assertEquals(testCollection.size(), 0);
        ClientMdb testEntity = new ClientMdb(TestData.getClient1());
        mongoDatabase.getCollection("testCollection", ClientMdb.class).insertOne(testEntity);
        testCollection = mongoDatabase.getCollection("testCollection", ClientMdb.class).find().into(new ArrayList<>());
        Assertions.assertEquals(1, testCollection.size());
        AbstractMongoRepository.decrementCounter();
    }

    @Test
    public void clientTypeAddTest() {
        MongoDatabase mongoDatabase = AbstractMongoRepository.getDatabase();
        mongoDatabase.getCollection("testCollection").drop();
        ArrayList<ClientTypeMdb> testCollection = mongoDatabase.getCollection("testCollection", ClientTypeMdb.class).find().into(new ArrayList<>());
        Assertions.assertEquals(testCollection.size(), 0);
        ClientTypeMdb testEntity = ClientMdb.clientTypeToClientTypeMdb(TestData.getClient1());
        mongoDatabase.getCollection("testCollection", ClientTypeMdb.class).insertOne(testEntity);
        testCollection = mongoDatabase.getCollection("testCollection", ClientTypeMdb.class).find().into(new ArrayList<>());
        Assertions.assertEquals(testEntity, testCollection.getFirst());
        AbstractMongoRepository.decrementCounter();
    }


    @Test
    public void connectionTest() {
        //given

        //when

        try {
            MongoDatabase mongoDatabase = AbstractMongoRepository.getDatabase();

            mongoDatabase.getCollection("testCollection").drop();

            Document document = new Document("1", 1);
            mongoDatabase.getCollection("testCollection").insertOne(document);
            ArrayList<Document> documents = mongoDatabase.getCollection("testCollection").find().into(new ArrayList<>());

            Assertions.assertEquals(1, documents.size());

            mongoDatabase.getCollection("testCollection").deleteOne(document);
            documents = mongoDatabase.getCollection("testCollection").find().into(new ArrayList<>());

            Assertions.assertEquals(0, documents.size());

            Document document2 = new Document("1", 2);
            documents.add(document);
            documents.add(document2);
            mongoDatabase.getCollection("testCollection").insertMany(documents);
            documents = mongoDatabase.getCollection("testCollection").find().into(new ArrayList<>());

            Assertions.assertEquals(2, documents.size());
            AbstractMongoRepository.decrementCounter();
        } catch (Exception e) {
            Assertions.fail(e.getMessage());
        }
    }

    @Test
    public void clientDeletingSuccessFully() {
        try (ClientRegisterManager clientRegisterManager = new ClientRegisterManager(testClientCollection)) {
            Assertions.assertEquals(0, clientRegisterManager.getAllClients().size());

            Client client1 = TestData.getClient1();
            Client client2 = TestData.getClient2();

            clientRegisterManager.clientRegister(client1);
            clientRegisterManager.clientRegister(client2);

            Assertions.assertEquals(2,
                    clientRegisterManager.getAllClients().size());

            clientRegisterManager.clientDelete(clientRegisterManager.getAllClients().getFirst());

            Assertions.assertEquals(1,
                    clientRegisterManager.getAllClients().size());
        } catch (Exception e) {
            Assertions.fail(e.getMessage());
        }
    }

    @Test
    public void clientUpdateAddress() {
        try (ClientRegisterManager clientRegisterManager = new ClientRegisterManager(testClientCollection)) {
            Client client = TestData.getClient1();
            clientRegisterManager.clientRegister(client);
            Address address = new Address(
                    "Warszawa", "Ksiestwo Warszawskie",
                    "14-10", "Grunwaldzka", "1");
            client = clientRegisterManager.getAllClients().getFirst();
            clientRegisterManager.clientUpdateAddress(client, address);
            Assertions.assertEquals(address,
                    clientRegisterManager.getAllClients().getFirst().getAddress());
        } catch (Exception e) {
            Assertions.fail(e.getMessage());
        }
    }

    @AfterAll
    public static void closeVirtualConnection() {
        AbstractMongoRepository.decrementCounter();
    }
}
