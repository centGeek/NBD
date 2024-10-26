package shop.orm.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import shop.orm.model.Client;
import shop.orm.model.Product;

import java.util.ArrayList;
import java.util.List;

@Getter
public class Purchase {

    private Long id;

    private Client client;

    public Purchase() {
    }

    public Purchase(Client client, List<Product> products) {
        this.client = client;
        this.products = products;
    }

    private List<Product> products = new ArrayList<>();
    @Override
    public String toString() {
        return "Purchase{" + " products=" + products +
                '}';
    }
}
