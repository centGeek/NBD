package shop.redis.repository.entity.redisEntities;

import lombok.Getter;
import lombok.Setter;
import shop.redis.model.*;

import java.util.UUID;

@Setter
@Getter
public class ClientRedis {
    // Getters and Setters
    private String id;
    private ClientTypeRedis clientTypeRedis;
    private AddressRedis addressRedis;

    // Constructor from Client
    public ClientRedis(Client client) {
        this.id = client.getId().toString();
        this.clientTypeRedis = clientTypeToClientTypeRedis(client);
        this.addressRedis = new AddressRedis(client.getAddress(), client.getId().toString());
    }

    // Constructor from Redis data (e.g., when deserialized from Redis)
    public ClientRedis(String id, ClientTypeRedis clientTypeRedis, AddressRedis addressRedis) {
        this.id = id;
        this.clientTypeRedis = clientTypeRedis;
        this.addressRedis = addressRedis;
    }

    // Convert Client to ClientTypeRedis
    public static ClientTypeRedis clientTypeToClientTypeRedis(Client client) {
        ClientType clientType = client.getClientType();
        ClientTypeRedis clientTypeRedis = null;

        String pesel = clientType.getPesel();

        if (clientType instanceof IndividualClient individualClient) {
            String email = individualClient.getEmail();
            String birthDate = individualClient.getBirthDate().toString();

            clientTypeRedis = new IndividualClientRedis(client.getId().toString(), pesel, email, birthDate);
        } else if (clientType instanceof CompanyClient companyClient) {
            String companyName = companyClient.getCompanyName();
            String nip = Long.toString(companyClient.getNIP());

            clientTypeRedis = new CompanyClientRedis(client.getId().toString(), pesel, companyName, nip);
        }

        return clientTypeRedis;
    }

    // Convert ClientTypeRedis to ClientType
    public static ClientType clientTypeRedisToClientType(ClientTypeRedis clientTypeRedis) {
        ClientType clientType = null;

        String pesel = clientTypeRedis.getPesel();

        if (clientTypeRedis instanceof IndividualClientRedis individualClientRedis) {
            String email = individualClientRedis.getEmail();
            String birthDate = individualClientRedis.getBirthDate();
            clientType = new IndividualClient(pesel, email, LocalDate.parse(birthDate));
        } else if (clientTypeRedis instanceof CompanyClientRedis companyClientRedis) {
            String companyName = companyClientRedis.getCompanyName();
            String nip = companyClientRedis.getNip();

            clientType = new CompanyClient(pesel, Long.parseLong(nip), companyName);
        }

        return clientType;
    }
    public static Client clientRedisToClient(ClientRedis clientRedis) {
        Address address = AddressRedis.addressRedisToAddress(clientRedis.getAddressRedis());
        return new Client(UUID.fromString(clientRedis.getId()), address, clientTypeRedisToClientType(clientRedis.getClientTypeRedis()));
    }
}
