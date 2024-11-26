package shop.redis.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@NoArgsConstructor
@Setter
public class Client {

    public Client(Address address, ClientType clientType) {
        this.id = UUID.randomUUID();
        this.address = address;
        this.clientType = clientType;
    }

    public Client(UUID id, Address address, ClientType clientType) {
        this.id = id;
        this.address = address;
        this.clientType = clientType;
    }

    private UUID id;

    @Setter
    private Address address;

    private ClientType clientType;

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Client{");
        sb.append("id=").append(id);
        sb.append(", address=").append(address);
        sb.append(", clientType=").append(clientType);
        sb.append('}');
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Client client = (Client) o;

        if (!address.equals(client.address)) return false;
        return clientType.equals(client.clientType);
    }

    @Override
    public int hashCode() {
        int result = address.hashCode();
        result = 31 * result + clientType.hashCode();
        return result;
    }
}
