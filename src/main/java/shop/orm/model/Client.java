package shop.orm.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Objects;
import java.util.UUID;


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

    public Client() {
    }

    @Getter
    private UUID id;

    @Setter
    @Getter
    private Address address;

    @Getter
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
        return Objects.equals(getId(), client.getId()) && Objects.equals(getAddress(), client.getAddress()) && Objects.equals(getClientType(), client.getClientType());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getAddress(), getClientType());
    }
}

