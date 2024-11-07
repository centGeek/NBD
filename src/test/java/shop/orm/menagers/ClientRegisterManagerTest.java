package shop.orm.menagers;

import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import shop.orm.TestData;
import shop.orm.model.Address;
import shop.orm.model.Client;
import shop.orm.repository.AbstractMongoRepository;
import shop.orm.repository.MongoDBClasses.AbstractEntityMdb;

import java.util.ArrayList;

public class ClientRegisterManagerTest {

    @Test
    @org.junit.jupiter.api.Order(2)
    public void addingTestCorrectly()  {
        try (ClientRegisterManager clientRegisterManager = new ClientRegisterManager()) {
            //clientRegisterManager.clientDelete(TestData.getClient2());
            Client client = TestData.getClient1();
            clientRegisterManager.clientRegister(client);
            Assertions.assertEquals(1, clientRegisterManager.getAllClients().size());

            clientRegisterManager.clientRegister(client);

            Assertions.assertEquals(1, clientRegisterManager.getAllClients().size());

            client = TestData.getClient3();
            clientRegisterManager.clientRegister(client);
            System.out.println(clientRegisterManager.getAllClients());

            Assertions.assertEquals(2, clientRegisterManager.getAllClients().size());
        }
        catch (Exception e) {
            Assertions.fail(e.getMessage());
        }
    }
        @Test
    public void connectionTest()  {
        //given

            //when


            try{
                MongoDatabase mongoDatabase = AbstractMongoRepository.getDatabase();

                mongoDatabase.getCollection("testCollection").drop();

                Document document = new Document("1",1);
                mongoDatabase.getCollection("testCollection").insertOne(document);
                ArrayList<Document> documents = mongoDatabase.getCollection("testCollection").find().into(new ArrayList<>());

                Assertions.assertEquals(1, documents.size());

                mongoDatabase.getCollection("testCollection").deleteOne(document);
                documents = mongoDatabase.getCollection("testCollection").find().into(new ArrayList<>());

                Assertions.assertEquals(0, documents.size());

                Document document2 = new Document("1",2);
                documents.add(document);
                documents.add(document2);
                mongoDatabase.getCollection("testCollection").insertMany(documents);
                documents = mongoDatabase.getCollection("testCollection").find().into(new ArrayList<>());

                Assertions.assertEquals(2, documents.size());

                //clientRegisterManager.clientRegister(TestData.getClient1());
                //Document doc = new Document();
                //doc.append("id","125");
                //Document doc2 = new Document();
                //doc2.append("id","126");
                //MongoDatabase database = AbstractMongoRepository.getDatabase();
                //database.getCollection("testCollection").insertOne(doc);
                //database.getCollection("testCollection").insertOne(doc2);
                //ArrayList<Document> documents = database.getCollection("testCollection").find().into(new ArrayList<>());
                //Assertions.assertEquals(documents.size(), 2 );

                AbstractMongoRepository.decrementCounter();
            }
            catch (Exception e) {
                Assertions.fail(e.getMessage());
            }
        }

//    @Test
//    @org.junit.jupiter.api.Order(1)
//    public void clientDeletingSuccessFully() {
//        try (ClientRegisterManager clientRegisterManager = new ClientRegisterManager()) {
//            Assertions.assertEquals(0, clientRegisterManager.getAllClients().size());
//
//            Client client1 = TestData.getClient1();
//            Client client2 = TestData.getClient2();
//
//            clientRegisterManager.clientRegister(client1);
//            clientRegisterManager.clientRegister(client2);
//            Assertions.assertEquals(2,
//                    clientRegisterManager.getAllClients().size());
//            clientRegisterManager.clientDelete(
//                    clientRegisterManager.getAllClients().get(0));
//            Assertions.assertEquals(1,
//                    clientRegisterManager.getAllClients().size());
//        } catch (Exception e) {
//            Assertions.fail(e.getMessage());
//        }
//    }
//
//    @Test
//    @org.junit.jupiter.api.Order(3)
//    public void clientUpdateAddress() {
//
//        try (ClientRegisterManager clientRegisterManager = new ClientRegisterManager()) {
//            Client client = TestData.getClient1();
//
//            clientRegisterManager.clientRegister(client);
//            Address address = new Address(
//                    "Warszawa", "Ksiestwo Warszawskie",
//                    "14-10", "Grunwaldzka", "1");
//            client = clientRegisterManager.getAllClients().get(0);
//            clientRegisterManager.clientUpdateAddress(client, address);
//            Assertions.assertEquals(address,
//                    clientRegisterManager.getAllClients().get(0).getAddress());
//
//        } catch (Exception e) {
//            Assertions.fail(e.getMessage());
//        }
//    }

}
