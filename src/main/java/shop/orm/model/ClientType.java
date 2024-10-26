package shop.orm.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import shop.orm.model.Client;

import java.util.List;

@Getter
public abstract class ClientType {

    private Long id;

    private String pesel;
    private Client client;

    public ClientType(String pesel) {
        this.pesel = pesel;
    }

    public ClientType() {

    }
}
