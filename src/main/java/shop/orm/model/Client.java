package shop.orm.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "Client")
@Access(AccessType.FIELD)
public class Client {
    public Client() {

    }
    public Client(Address address, ClientType clientType) {
        this.address = address;
        this.clientType = clientType;
    }
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    @Getter
    @OneToOne
    @JoinColumn(name = "address_id", nullable = false)
    private Address address;

    @OneToOne
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

