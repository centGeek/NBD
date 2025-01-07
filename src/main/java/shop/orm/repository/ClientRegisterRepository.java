package shop.orm.repository;

import com.datastax.oss.driver.api.core.CqlIdentifier;
import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.cql.ResultSet;
import com.datastax.oss.driver.api.core.cql.Row;
import com.datastax.oss.driver.api.core.cql.SimpleStatement;
import com.datastax.oss.driver.api.core.metadata.schema.ClusteringOrder;
import com.datastax.oss.driver.api.core.type.DataTypes;
import com.datastax.oss.driver.api.querybuilder.QueryBuilder;
import com.datastax.oss.driver.api.querybuilder.SchemaBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import shop.orm.model.Address;
import shop.orm.model.Client;
import shop.orm.repository.classes.AddressCassandra;
import shop.orm.repository.classes.CassandraConsts;
import shop.orm.repository.classes.ClientCassandra;
import shop.orm.repository.dao.ClientDao;
import shop.orm.repository.mapper.ClientMapper;
import shop.orm.repository.mapper.ClientMapperBuilder;

import java.util.ArrayList;
import java.util.List;

public class ClientRegisterRepository extends AbstractCassandraRepository {

    private final String nameOfCollection;
    private final CqlSession session;
    protected static final Logger logger = LogManager.getLogger(ClientRegisterRepository.class);

    public ClientRegisterRepository() {
        this.session = AbstractCassandraRepository.getDatabase();
        this.nameOfCollection = "clients";
        createTables();
    }

    private void createTables() {
        //Drop and create

        SimpleStatement dropTableClient = SchemaBuilder.dropTable(CqlIdentifier.fromCql(CassandraConsts.CLIENT_TABLE_NAME)).ifExists().build();
        session.execute(dropTableClient);

        SimpleStatement dropTypeAddress = SchemaBuilder.dropType(CqlIdentifier.fromCql(CassandraConsts.ADDRESS_TABLE_NAME)).ifExists().build();
        session.execute(dropTypeAddress);

        //Creating "type" address
        SimpleStatement crateAddressType = SchemaBuilder.createType(CassandraConsts.ADDRESS_TABLE_NAME_CQL).ifNotExists()
                .withField(CqlIdentifier.fromCql("id"), DataTypes.UUID)
                .withField(CqlIdentifier.fromCql("country"), DataTypes.TEXT)
                .withField(CqlIdentifier.fromCql("city"), DataTypes.TEXT)
                .withField(CqlIdentifier.fromCql("street"), DataTypes.TEXT)
                .withField(CqlIdentifier.fromCql("postal_code"), DataTypes.TEXT)
                .withField(CqlIdentifier.fromCql("street_number"), DataTypes.TEXT)
                .build();
        session.execute(crateAddressType);

        //Creating ClientTable
        SimpleStatement createClientTable = SchemaBuilder.createTable(CassandraConsts.CLIENT_TABLE_NAME_CQL).ifNotExists()
                .withPartitionKey(CassandraConsts.CLIENT_ID, DataTypes.UUID)
                .withColumn(CassandraConsts.ADDRESSES_FIELD, SchemaBuilder.udt(CassandraConsts.ADDRESS_DATA_TYPE, true))
                .build();
        session.execute(createClientTable);

    }


//    public ClientRegisterRepository(String nameOfCollection) {
//        this.session = AbstractCassandraRepository.getDatabase();
//        this.nameOfCollection = nameOfCollection;
//        MongoCollection<Document> collection = database.getCollection(nameOfCollection);
//        collection.drop();
//        database.createCollection(nameOfCollection);
//    }


    public void clientRegister(Client client) {
//        try {
        String pesel = client.getClientType().getPesel();


//            List<ClientMdb> clientByPesel = this.getClientByPesel(pesel);
//            if (clientByPesel.isEmpty()) {

        ClientMapper clientMapper = new ClientMapperBuilder(session).build();
        ClientDao clientDao = clientMapper.clientDao();
        ClientCassandra clientCassandra = new ClientCassandra(client);
        clientDao.insert(clientCassandra);

//            } else {
//                logger.log(Level.ERROR, String.format("Can not register client. Client with pesel: %s already exists", pesel));
//                throw new RuntimeException("Can not register client. Client with pesel: " + pesel + " already exists");
//            }
//        } catch (Exception e) {
//            logger.log(Level.ERROR, "Registering client did not went correctly");
//            throw new RuntimeException(e);
//        }
    }

    public void clientDelete(Client client) {
//        try {
        ClientMapper clientMapper = new ClientMapperBuilder(session).build();
        ClientDao clientDao = clientMapper.clientDao();
        clientDao.delete(new ClientCassandra(client));


//            Bson filter = Filters.eq("_id", client.getId().toString());
//            collection.findOneAndDelete(filter);
//        } catch (Exception e) {
//            logger.log(Level.ERROR, e);
//        }
    }

    public long count() {
        ClientMapper clientMapper = new ClientMapperBuilder(session).build();
        ClientDao clientDao = clientMapper.clientDao();
        return clientDao.count();
    }

    public void clientUpdateAddress(Client client, Address address) {
//
//        MongoCollection<ClientMdb> collection = database.getCollection(nameOfCollection, ClientMdb.class);
//        Bson filter = Filters.eq("_id", client.getId().toString());
//        ArrayList<ClientMdb> arrayList = collection.find(filter).into(new ArrayList<>());
//        if (arrayList.size() == 1) {
//            Bson update = Updates.set("address", new AddressMdb(address, client.getId().toString()));
//            collection.updateOne(filter, update);
//            client.setAddress(address);
//        }
    }

//    public List<Client> getAllClients() {
//
//        ClientMapper clientMapper = new ClientMapperBuilder(session).build();
//        ClientDao clientDao = clientMapper.clientDao();
//
//        ResultSet resultSet = clientDao.getAllClients();
//
//        List<ClientCassandra> clientCassandraList = new ArrayList<>();
//
//        List<Client> clients = new ArrayList<>();
//
//        for (Row row : resultSet)
//        {
//            clientCassandraList.add(new ClientCassandra(row.getUuid(CassandraConsts.CLIENT_ID_NAME),row.get(CassandraConsts.ADDRESS_DATA_TYPE,AddressCassandra.class)));
//        }
//
//        for (ClientCassandra clientCassandra : clientCassandraList) {
//
//
//            clients.add(clientCassandra.toClient());
////                    UUID.fromString(clientMdb.getEntityId()), AddressMdb.AddresMdbToAddress(clientMdb.getAddressMdb()),
////                    new IndividualClient("03222222111", "email2@gmail.com",
////                            LocalDate.of(2022, 10, 21))));
//        }
//
//        return clients;
//    }

}
