package shop.redis.repository.mongoEntity;

import org.bson.codecs.pojo.annotations.BsonCreator;
import org.bson.codecs.pojo.annotations.BsonProperty;
import shop.redis.model.*;

import java.time.LocalDate;
import java.util.UUID;

public class ClientMdb extends AbstractEntityMdb {
    @BsonCreator
    public ClientMdb(@BsonProperty("_id") String _id,
                     @BsonProperty("clientType") ClientTypeMdb clientType,
                     @BsonProperty("address") AddressMdb addresMdb
    ) {
        super(_id);
        this.addressMdb = addresMdb;
        this.clientTypeMdb = clientType;
    }

    public ClientMdb(Client client) {
        super(client.getId().toString());
        this.clientTypeMdb = clientTypeToClientTypeMdb(client);
        this.addressMdb = new AddressMdb(client.getAddress(), client.getId().toString());
    }


    @BsonProperty("clientType")
    private ClientTypeMdb clientTypeMdb;

    @BsonProperty("address")
    private AddressMdb addressMdb;

    public AddressMdb getAddressMdb() {
        return addressMdb;
    }


    public ClientTypeMdb getClientTypeMdb() {
        return clientTypeMdb;
    }

    public static ClientTypeMdb clientTypeToClientTypeMdb(Client client) {
        ClientType clientType = client.getClientType();
        ClientTypeMdb clientTypeMdb = null;

        String pesel = clientType.getPesel();


        if (clientType instanceof IndividualClient individualClient) {
            String email = individualClient.getEmail();
            String birthDate = individualClient.getBirthDate().toString();

            clientTypeMdb = new IndividualClientMdb(client.getId().toString(), pesel, email, birthDate);
        } else if (clientType instanceof CompanyClient companyClient) {
            String companyName = companyClient.getCompanyName();
            String nip = Long.toString(companyClient.getNIP());

            clientTypeMdb = new CompanyClientMdb(client.getId().toString(), clientType.getPesel(), companyName, nip);
        }

        return clientTypeMdb;
    }


    public static ClientType clientTypeMdbToClientType(ClientTypeMdb clientTypeMdb) {

        ClientType clientType = null;

        String pesel = clientTypeMdb.getPesel();


        if (clientTypeMdb instanceof IndividualClientMdb individualClientMdb) {
            String email = individualClientMdb.getEmail();
            String birthDate = individualClientMdb.getBirthData();
            clientType = new IndividualClient(pesel, email, LocalDate.parse(birthDate));

        } else if (clientTypeMdb instanceof CompanyClientMdb companyClientMdb) {
            String companyName = companyClientMdb.getCompanyNameMdb();
            String nip = companyClientMdb.getNIPMdb();

            clientType = new CompanyClient(clientTypeMdb.getPesel(), Long.parseLong(nip, 10), companyName);
        }

        return clientType;
    }

    public static Client ClientMdbToClient(ClientMdb clientMdb) {
        AddressMdb addressMdb = clientMdb.getAddressMdb();
        Address address = AddressMdb.AddresMdbToAddress(addressMdb);
        return new Client(UUID.fromString(clientMdb.getEntityId()), address, clientTypeMdbToClientType(clientMdb.getClientTypeMdb()));
    }

}


