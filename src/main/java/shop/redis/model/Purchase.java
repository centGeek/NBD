package shop.redis.model;


import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@NoArgsConstructor
@Setter
@EqualsAndHashCode
@ToString
public class Purchase {

    private UUID id;

    private Client client;


    public Purchase(Client client, List<Product> products) {
        id = UUID.randomUUID();
        this.client = client;
        this.products = products;
    }

    private List<Product> products = new ArrayList<>();
}
