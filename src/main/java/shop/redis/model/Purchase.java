package shop.redis.model;


import lombok.Getter;


import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Getter
public class Purchase {

    private UUID id;

    private Client client;


    public Purchase(Client client, List<Product> products) {
        id = UUID.randomUUID();
        this.client = client;
        this.products = products;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Purchase purchase = (Purchase) o;
        return Objects.equals(getId(), purchase.getId()) && Objects.equals(getClient(), purchase.getClient()) && Objects.equals(getProducts(), purchase.getProducts());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getClient(), getProducts());
    }

    private List<Product> products = new ArrayList<>();

    @Override
    public String toString() {
        return "Purchase{" + " products=" + products +
                '}';
    }
}
