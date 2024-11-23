package shop.redis.model;

import lombok.Getter;


@Getter
public abstract class ClientType {

    private Long id;

    private String pesel;
    private Client client;

    public ClientType(String pesel) {
        this.pesel = pesel;
    }
}
