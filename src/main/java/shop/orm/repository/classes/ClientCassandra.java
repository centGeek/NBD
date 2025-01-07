package shop.orm.repository.classes;

import com.datastax.oss.driver.api.mapper.annotations.CqlName;
import com.datastax.oss.driver.api.mapper.annotations.Entity;
import com.datastax.oss.driver.api.mapper.annotations.PartitionKey;
import com.datastax.oss.driver.api.mapper.annotations.PropertyStrategy;
import com.datastax.oss.driver.api.mapper.entity.naming.GetterStyle;
import lombok.Getter;
import lombok.Setter;
import shop.orm.model.Address;
import shop.orm.model.Client;
import shop.orm.model.ClientType;
import shop.orm.model.CompanyClient;

import java.util.UUID;


@Entity(defaultKeyspace = CassandraConsts.DEFAULT_NAMESPACE)
@CqlName(CassandraConsts.CLIENT_TABLE_NAME)
@PropertyStrategy(mutable = false, getterStyle = GetterStyle.JAVABEANS)
public class ClientCassandra {

    public ClientCassandra(UUID id, AddressCassandra address) {
        this.address = address;
        this.id = id;
    }

    public ClientCassandra(Client client) {
        this.id = client.getId();
        this.address = new AddressCassandra(client.getAddress());
    }
    @CqlName(CassandraConsts.CLIENT_ID_NAME)
    @PartitionKey
    private UUID id;


    private AddressCassandra address;
//    @Getter
//    private ClientType clientType;


    public static Client ClientCassandraToClient(ClientCassandra cassandra) {
        //TODO poprawic
        Client client = new Client(cassandra.getAddress().toAddress(),new CompanyClient("1251251251",125,"asdasd"));
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
}