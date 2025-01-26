package shop.orm.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Getter
public class Purchase {

    private UUID id;
    private Client client;
    private List<Product> products;

    // Konstruktor wymagający dla Jacksona
    @JsonCreator
    public Purchase(@JsonProperty("client") Client client, @JsonProperty("products") List<Product> products) {
        this.id = UUID.randomUUID();
        this.client = client;
        this.products = products;
    }

    public Purchase() {
        this.id = UUID.randomUUID();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Purchase purchase = (Purchase) o;
        return Objects.equals(id, purchase.id) &&
                Objects.equals(client, purchase.client) &&
                Objects.equals(products, purchase.products);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, client, products);
    }

    @Override
    public String toString() {
        return "Purchase{" + "client=" + client + ", products=" + products + '}';
    }
}
