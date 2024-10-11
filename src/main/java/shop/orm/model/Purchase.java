package shop.orm.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import shop.orm.model.Client;
import shop.orm.model.Product;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Access(AccessType.FIELD)
public class Purchase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Client client;

    public Purchase() {
    }

    public Purchase(Client client, List<Product> products) {
        this.client = client;
        this.products = products;
    }

    @ManyToMany
    @JoinTable(
            name = "purchase_product",
            joinColumns = @JoinColumn(name = "purchase_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id")
    )
    private List<Product> products = new ArrayList<>();

    @Override
    public String toString() {
        return "Purchase{" + "client=" + client +
                ", products=" + products +
                '}';
    }
}
