package shop.orm.repository.MongoDBClasses;

import org.bson.codecs.pojo.annotations.BsonCreator;
import org.bson.codecs.pojo.annotations.BsonProperty;
import shop.orm.model.Address;
import shop.orm.model.Client;
import shop.orm.model.ClientType;
import shop.orm.model.CompanyClient;

import java.time.LocalDate;
import java.util.UUID;

public class ClientMdb extends AbstractEntityMdb {
    @BsonCreator
    public ClientMdb(@BsonProperty("_id") String _id,
                     //@BsonProperty("clientType") ClientTypeMdb clientType,
                     @BsonProperty("address") AddressMdb addresMdb
    ) {
        super(_id);
        this.addressMdb = addresMdb;
        //this.clientTypeMdb = clientType;
    }

    public ClientMdb(Client client) {
        super(client.getId().toString());
        //this.clientTypeMdb = clientTypeToClientTypeMdb(client.getClientType());
        this.addressMdb = new AddressMdb(client.getAddress(),client.getId().toString());
    }


    //@BsonProperty("clientType")
    //private ClientTypeMdb clientTypeMdb;

    @BsonProperty("address")
    private AddressMdb addressMdb;

    public AddressMdb getAddressMdb() {
        return addressMdb;
    }


    //    public ClientTypeMdb getClientTypeMdb() {
//        return clientTypeMdb;
//    }

//    public AddressMdb getAddressMdb() {
//        return addressMdb;
//    }

//    private ClientTypeMdb clientTypeToClientTypeMdb(ClientType clientType) {
//        ClientTypeMdb clientTypeMdb = null;
//
//        String clientTypeString = clientType.toString();
//
//        if (clientTypeString.startsWith("IndividualClient")) {
//            // Parsowanie informacji z IndividualClient
//            String email = extractValue(clientTypeString, "email='", "'");
//            String birthDate = extractValue(clientTypeString, "birthDate=", "}");
//
//            clientTypeMdb = new IndividualClientMdb(email, birthDate);
//
//        } else if (clientTypeString.startsWith("CompanyClient")) {
//            // Parsowanie informacji z CompanyClient
//            String companyName = extractValue(clientTypeString, "companyName='", "'");
//            String nip = extractValue(clientTypeString, "NIP=", "}");
//
//            clientTypeMdb = new CompanyClientMdb(companyName, Long.parseLong(nip));
//        }
//
//        return clientTypeMdb;
//    }

    // Metoda pomocnicza do wyciągania wartości z toString()
//    private String extractValue(String source, String prefix, String suffix) {
//        int startIndex = source.indexOf(prefix) + prefix.length();
//        int endIndex = source.indexOf(suffix, startIndex);
//        return source.substring(startIndex, endIndex);
//    }

    //TODO tutaj zacząć ogarniać to ClientType
//    public static Client ClientMdbToClient (ClientMdb clientMdb){
//       AddressMdb addressMdb = clientMdb.getAddressMdb();
//        Address address = AddressMdb.AddresMdbToAddress(addressMdb);
//        return new Client(address);
//    }

}


