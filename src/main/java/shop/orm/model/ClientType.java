package shop.orm.model;

import lombok.Getter;


@Getter
public abstract class ClientType {

    private String pesel;

    public ClientType(String pesel) {
        this.pesel = pesel;
    }
}
