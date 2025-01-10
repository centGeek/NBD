package shop.orm.repository;

import com.datastax.oss.driver.api.core.CqlIdentifier;
import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.PagingIterable;
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
import shop.orm.repository.classes.ClientTypeConsts;
import shop.orm.repository.dao.ClientDao;
import shop.orm.repository.mapper.ClientMapper;
import shop.orm.repository.mapper.ClientMapperBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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

        SimpleStatement dropTypeClientType = SchemaBuilder.dropType(CassandraConsts.CLIENT_TYPE_TABLE_NAME).ifExists().build();
        session.execute(dropTypeClientType);

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

        SimpleStatement createClientType = SchemaBuilder.createType(CassandraConsts.CLIENT_TYPE_TABLE_NAME).ifNotExists()
                .withField(CqlIdentifier.fromCql("entity_id"),DataTypes.UUID)
                .withField(CqlIdentifier.fromCql("discriminator"), DataTypes.TEXT)
                .withField(ClientTypeConsts.PESEL_CQL, DataTypes.TEXT)
                .withField(ClientTypeConsts.COMPANY_NAME,DataTypes.TEXT)
                .withField(ClientTypeConsts.NIP,DataTypes.BIGINT)
                .withField(ClientTypeConsts.EMAIL,DataTypes.TEXT)
                .withField(ClientTypeConsts.BIRTHDATE,DataTypes.DATE)
                .build();
        session.execute(createClientType);

        //Creating ClientTable
        SimpleStatement createClientTable = SchemaBuilder.createTable(CassandraConsts.CLIENT_TABLE_NAME_CQL).ifNotExists()
                .withPartitionKey(CassandraConsts.CLIENT_ID, DataTypes.UUID)
                .withColumn(CassandraConsts.ADDRESSES_FIELD, SchemaBuilder.udt(CassandraConsts.ADDRESS_DATA_TYPE, true))
                .withColumn(CassandraConsts.CLIENT_TYPE_TABLE_NAME_CQL, SchemaBuilder.udt(CassandraConsts.CLIENT_TYPE_TABLE_NAME_CQL, true))
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
        ClientMapper clientMapper = new ClientMapperBuilder(session).build();
        ClientDao clientDao = clientMapper.clientDao();


        ClientCassandra clientCassandra = clientDao.findById(client.getId());
        if (clientCassandra != null) {
            clientDao.delete(clientCassandra);
            client.setAddress(address);
            clientCassandra = new ClientCassandra(client);
            clientDao.insert(clientCassandra);
        }
    }


    public List<Client> getAllClients() {

        ClientMapper clientMapper = new ClientMapperBuilder(session).build();
        ClientDao clientDao = clientMapper.clientDao();

        PagingIterable<ClientCassandra> clientCassandraList = clientDao.getAllClients();

        List<Client> clients = new ArrayList<>();

        for (ClientCassandra clientCassandra : clientCassandraList) {

            clients.add(clientCassandra.toClient());
//                    UUID.fromString(clientMdb.getEntityId()), AddressMdb.AddresMdbToAddress(clientMdb.getAddressMdb()),
//                    new IndividualClient("03222222111", "email2@gmail.com",
//                            LocalDate.of(2022, 10, 21))));
        }

        return clients;
    }

    public Client findById(UUID id) {
        ClientMapper clientMapper = new ClientMapperBuilder(session).build();
        ClientDao clientDao = clientMapper.clientDao();
        ClientCassandra clientCassandra = clientDao.findById(id);

        return clientCassandra.toClient();
    }
}
