package shop.redis.model;


import lombok.*;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.UUID;

@Setter
@Getter
public class Product {
    @Setter(AccessLevel.NONE)
    private UUID id;

    private String productName;

    private BigDecimal price;

    private boolean isProductBought;

    private Purchase purchase;

    public Product() {

    }
    public Product(String productName, BigDecimal price, boolean isProductBought,UUID uuid ) {
        this.productName = productName;
        this.price = price;
        this.isProductBought = isProductBought;
        this.id = uuid;
    }

    public Product(String productName, BigDecimal price) {
        this.productName = productName;
        this.price = price;
        this.isProductBought = false;
        this.id = UUID.randomUUID();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return isProductBought() == product.isProductBought() && Objects.equals(getId(), product.getId()) && Objects.equals(getProductName(), product.getProductName()) && Objects.equals(getPrice(), product.getPrice()) && Objects.equals(getPurchase(), product.getPurchase());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getProductName(), getPrice(), isProductBought(), getPurchase());
    }

    @Override
    public String toString() {
        return "Product{" + "productName='" + productName + '\'' +
                ", price=" + price +
                ", isProductBought=" + isProductBought +
                '}';
    }
}

