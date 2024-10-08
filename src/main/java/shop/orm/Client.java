package shop.orm;

import jakarta.persistence.*;

@Entity
@Table(name = "Client")
@Access(AccessType.FIELD)


public class Client {

    @Id
    private Long id;

    @ManyToOne
    private ClientType clientType;
}
