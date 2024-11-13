package shop.orm.repository;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bson.conversions.Bson;
import shop.orm.model.Address;
import shop.orm.model.Client;
import shop.orm.model.ClientType;
import shop.orm.model.IndividualClient;
import shop.orm.repository.MongoDBClasses.ClientMdb;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ClientRegisterRepository extends AbstractMongoRepository {

    private final MongoDatabase database;

    protected static final Logger logger = LogManager.getLogger(ClientRegisterRepository.class);

    public ClientRegisterRepository() {
        this.database = AbstractMongoRepository.getDatabase();
    }


    public void clientRegister(Client client) {
        try {
            String pesel = client.getClientType().getPesel();


            //List<ClientType> clientByPesel = this.getClientByPesel(pesel);
            //if (clientByPesel.isEmpty()) {
            MongoCollection<ClientMdb> collection = database.getCollection("clients", ClientMdb.class);
            ClientMdb clientMdb = new ClientMdb(client);
            collection.insertOne(clientMdb);
//                entityManager.getTransaction().begin();
//                entityManager.persist(client.getAddress());
//                entityManager.persist(client.getClientType());
//                entityManager.persist(client);
//                entityManager.getTransaction().commit();
            //} else {
            //    logger.log(Level.ERROR, String.format("Can not register client. Client with pesel: %s already exists", pesel));
            //}
        } catch (Exception e) {
            logger.log(Level.ERROR, "Registering client did not went correctly");
        }
    }

    public void clientDelete(Client client) {
        try {
            MongoCollection<ClientMdb> collection = database.getCollection("clients", ClientMdb.class);
            Bson filter = Filters.eq("client",client.getId().toString());
            collection.findOneAndDelete(filter);
        } catch (Exception e) {
            //    if (entityManager.getTransaction().isActive()) {
            //        entityManager.getTransaction().rollback();
            //    }
            logger.log(Level.ERROR, e);
        }
    }

    public void clientUpdateAddress(Client client, Address address) {
        try {
            //entityManager.getTransaction().begin();

//            if (address.getAddressId() == null) {
//                //    entityManager.persist(address);
//            }
//            client.setAddress(address);
            //entityManager.merge(client);
            //entityManager.getTransaction().commit();
        } catch (Exception e) {
//            if (entityManager.getTransaction().isActive()) {
//                entityManager.getTransaction().rollback();
//            }
            logger.log(Level.ERROR, e);
        }
    }

    public List<Client> getAllClients() {
        //String selectQuery = "SELECT c FROM Client c";
        //entityManager.getTransaction().begin();
        //Query query = entityManager.createQuery(selectQuery);
        //List<Client> clients = query.getResultList();
        //entityManager.getTransaction().commit();
        //return clients;



        MongoCollection<ClientMdb> clientMdbMongoCollection = database.getCollection("clients", ClientMdb.class);
        ArrayList<ClientMdb> clientMdbs = clientMdbMongoCollection.find().into(new ArrayList<>());
        Address address = new Address("Zgierz", "Poland", "12-001", "Zbierzowa", "7");
        ArrayList<Client> clients = new ArrayList<>();
        for (ClientMdb clientMdb : clientMdbs) {
            clients.add(new Client(address, new IndividualClient("03222222111", "email2@gmail.com", LocalDate.of(2022, 10, 21))));
        }

        return clients;
    }

    public List<ClientType> getClientByPesel(String pesel) {
        //String selectQuery = "SELECT ct FROM ClientType ct where ct.pesel =:pesel";
        //TypedQuery<ClientType> query = entityManager.createQuery(selectQuery, ClientType.class);
        //query.setParameter("pesel", pesel);
        //return query.getResultList();
        return null;
    }


}
