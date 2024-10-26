package shop.orm.model;

import lombok.Getter;
import lombok.Setter;


public class Client {
    public Client() {

    }
    public Client(Address address, ClientType clientType) {
        this.address = address;
        this.clientType = clientType;
    }
    @Getter
    private Long id;

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
}

