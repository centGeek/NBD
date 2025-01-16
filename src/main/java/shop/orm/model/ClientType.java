package shop.orm.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.Objects;


@Getter

public abstract class ClientType {

    private String pesel;

    public ClientType(String pesel) {
        this.pesel = pesel;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ClientType that = (ClientType) o;
        return Objects.equals(getPesel(), that.getPesel());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getPesel());
    }
}
