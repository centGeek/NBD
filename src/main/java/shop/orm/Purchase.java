package shop.orm;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Purchase {

    @ManyToOne
    private Client client;

    @OneToMany
    private List<Product> products = new ArrayList<>();
    @Id
    private Long id;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
