package shop.orm.model;


import lombok.*;

import java.math.BigDecimal;

@Setter
@Getter
@EqualsAndHashCode
public class Product {
    private Long id;

    private String productName;

    private BigDecimal price;

    private boolean isProductBought;

    private Long version;

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

