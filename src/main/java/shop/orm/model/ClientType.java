package shop.orm.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import shop.orm.model.Client;

import java.util.List;

@Entity
@Getter
@Inheritance(strategy = InheritanceType.JOINED)
@Access(AccessType.FIELD)
public abstract class ClientType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "pesel")
    private String pesel;
    @OneToOne
    private Client client;

    public ClientType(String pesel) {
        this.pesel = pesel;
    }

    public ClientType() {

    }
}
