package shop.orm.model;


import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Setter
@Getter
@EqualsAndHashCode
@Access(AccessType.FIELD)
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "product_name", nullable = false)
    private String productName;

    @Column(name = "price", nullable = false)
    private BigDecimal price;

    @Column(name = "is_product_bought", nullable = false)
    private boolean isProductBought;

    @ManyToOne
    @JoinColumn(name = "purchase_id")
    private Purchase purchase;

    public Product() {

    }

    public Product(String productName, BigDecimal price) {
        this.productName = productName;
        this.price = price;
        this.isProductBought = false;
    }

    @Override
    public String toString() {
        return "Product{" + "productName='" + productName + '\'' +
                ", price=" + price +
                ", isProductBought=" + isProductBought +
                '}';
    }
}

