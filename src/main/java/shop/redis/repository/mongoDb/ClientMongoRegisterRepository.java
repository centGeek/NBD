package shop.redis.repository.mongoDb;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bson.conversions.Bson;
import shop.redis.model.Address;
import shop.redis.model.Client;
import shop.redis.repository.mongoEntity.AddressMdb;
import shop.redis.repository.mongoEntity.ClientMdb;
import shop.redis.repository.redis.ClientRedisRepository;

import java.util.ArrayList;
import java.util.List;

public class ClientMongoRegisterRepository extends AbstractMongoRepository {

    private final MongoDatabase database;
    private final String nameOfCollection;

    protected static final Logger logger = LogManager.getLogger(ClientMongoRegisterRepository.class);

    public ClientMongoRegisterRepository() {
        this.database = AbstractMongoRepository.getDatabase();
        this.nameOfCollection = "clients";
    }

    public ClientMongoRegisterRepository(String nameOfCollection, ClientRedisRepository clientRedisRepository) {
        this.database = AbstractMongoRepository.getDatabase();
        this.nameOfCollection = nameOfCollection;
        var collection = database.getCollection(nameOfCollection);
        collection.drop();
        database.createCollection(nameOfCollection);
    }


    public void clientRegister(Client client) {
        try {
            var pesel = client.getClientType().getPesel();
            var clientByPesel = this.getClientByPesel(pesel);
            if (clientByPesel == null) {
                MongoCollection<ClientMdb> collection = database.getCollection(nameOfCollection, ClientMdb.class);
                ClientMdb clientMdb = new ClientMdb(client);
                collection.insertOne(clientMdb);
            } else {
                logger.log(Level.ERROR, String.format("Can not register client. Client with pesel: %s already exists", pesel));
                throw new RuntimeException("Can not register client. Client with pesel: " + pesel + " already exists");
            }
        } catch (Exception e) {
            logger.log(Level.ERROR, "Registering client did not went correctly");
            throw new RuntimeException(e);
        }
    }

    public void clientDelete(Client client) {
        try {
            MongoCollection<ClientMdb> collection = database.getCollection(nameOfCollection, ClientMdb.class);
            Bson filter = Filters.eq("_id", client.getId().toString());
            collection.findOneAndDelete(filter);
        } catch (Exception e) {
            logger.log(Level.ERROR, e);
        }
    }

    public void clientUpdateAddress(Client client, Address address) {

        MongoCollection<ClientMdb> collection = database.getCollection(nameOfCollection, ClientMdb.class);
        Bson filter = Filters.eq("_id", client.getId().toString());
        ArrayList<ClientMdb> arrayList = collection.find(filter).into(new ArrayList<>());
        if (arrayList.size() == 1) {
            Bson update = Updates.set("address", new AddressMdb(address, client.getId().toString()));
            collection.updateOne(filter, update);
            client.setAddress(address);
        }
    }

    public List<Client> getAllClients() {
        MongoCollection<ClientMdb> clientMdbMongoCollection = database.getCollection(nameOfCollection, ClientMdb.class);
        ArrayList<ClientMdb> clientMdbs = clientMdbMongoCollection.find().into(new ArrayList<>());
        ArrayList<Client> clients = new ArrayList<>();


        for (ClientMdb clientMdb : clientMdbs) {
            clients.add(ClientMdb.ClientMdbToClient(clientMdb));
//                    UUID.fromString(clientMdb.getEntityId()), AddressMdb.AddresMdbToAddress(clientMdb.getAddressMdb()),
//                    new IndividualClient("03222222111", "email2@gmail.com",
//                            LocalDate.of(2022, 10, 21))));
        }

        return clients;
    }

    public List<ClientMdb> getClientByPesel(String pesel) {

        MongoCollection<ClientMdb> collection = database.getCollection(nameOfCollection, ClientMdb.class);
        Bson filter = Filters.eq("clientType.pesel", pesel);
        ArrayList<ClientMdb> arrayList = collection.find(filter).into(new ArrayList<>());

        return arrayList;
    }
}
