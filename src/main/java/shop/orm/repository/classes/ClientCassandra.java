package shop.orm.repository.classes;

import com.datastax.oss.driver.api.mapper.annotations.CqlName;
import com.datastax.oss.driver.api.mapper.annotations.Entity;
import com.datastax.oss.driver.api.mapper.annotations.PartitionKey;
import com.datastax.oss.driver.api.mapper.annotations.PropertyStrategy;
import com.datastax.oss.driver.api.mapper.entity.naming.GetterStyle;
import shop.orm.model.*;

import java.util.UUID;


@Entity(defaultKeyspace = CassandraConsts.DEFAULT_NAMESPACE)
@CqlName(CassandraConsts.CLIENT_TABLE_NAME)
@PropertyStrategy(mutable = false, getterStyle = GetterStyle.JAVABEANS)
public class ClientCassandra {


    public ClientCassandra(Client client) {
        this.id = client.getId();
        this.address = new AddressCassandra(client.getAddress());
        this.clientTypeCassandra = clientTypeToClientTypeCassandra(client.getClientType());
    }

    public ClientCassandra(UUID id, AddressCassandra address, ClientTypeCassandra clientTypeCassandra) {
        this.id = id;
        this.address = address;
        this.clientTypeCassandra = clientTypeCassandra;
    }

    @CqlName(CassandraConsts.CLIENT_ID_NAME)
    @PartitionKey
    private UUID id;


    private AddressCassandra address;

    @CqlName(CassandraConsts.CLIENT_TYPE_TABLE_NAME)
    private ClientTypeCassandra clientTypeCassandra;


    public static Client ClientCassandraToClient(ClientCassandra cassandra) {
        //TODO poprawic
        Client client = new Client(cassandra.getId(), cassandra.getAddress().toAddress(), new CompanyClient("1251251251", 125, "asdasd"));
        return client;
    }

    public Client toClient() {
        return ClientCassandraToClient(this);
    }

    public UUID getId() {
        return id;
    }

    public AddressCassandra getAddress() {
        return address;
    }

    public ClientTypeCassandra getClientTypeCassandra() {
        return clientTypeCassandra;
    }

    private static ClientTypeCassandra clientTypeToClientTypeCassandra(ClientType clientType) {
        if (clientType instanceof CompanyClient) {
            CompanyClient companyClient = (CompanyClient) clientType;
            return new CompanyCassandra(UUID.randomUUID(), companyClient.getCompanyName(), companyClient.getNIP(), companyClient.getPesel(), "company");
        }
        if (clientType instanceof IndividualClient) {
            IndividualClient individualClient = (IndividualClient) clientType;
            return new IndividualClientCassandra(UUID.randomUUID(), individualClient.getEmail(), individualClient.getBirthDate(), individualClient.getPesel(), "individual");
        } else throw new IllegalArgumentException("Unsupported clientType");
    }
}