package shop.orm.repository.MongoDBClasses;


import org.bson.codecs.pojo.annotations.BsonCreator;
import org.bson.codecs.pojo.annotations.BsonProperty;
import shop.orm.model.Product;

import java.math.BigDecimal;
import java.util.UUID;


public class ProductMdb extends AbstractEntityMdb {


    @BsonCreator
    public ProductMdb(
            @BsonProperty("_id") String id,
            @BsonProperty("productName") String productName,
            @BsonProperty("price") BigDecimal price,
            @BsonProperty("productBoughtCounter") int productBoughtCounter
    ) {
        super(id);
        this.productName = productName;
        this.price = price;
        this.productBoughtCounter = productBoughtCounter;
    }

    public ProductMdb(Product product) {
        super(product.getId().toString());
        this.productName = product.getProductName();
        this.price = product.getPrice();
        this.productBoughtCounter = product.isProductBought() ? 1 : 0;
    }

    @BsonProperty("productName")
    private String productName;
    @BsonProperty("price")
    private BigDecimal price;
    @BsonProperty("productBoughtCounter")
    private int productBoughtCounter;


    public static Product productFromProductMdb(ProductMdb productMdb) {
        return new Product(productMdb.getProductName(), productMdb.getPrice(), productMdb.isProductCounterBought(), UUID.fromString(productMdb.getEntityId()));
    }

    public String getProductName() {
        return productName;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public int getProductBoughtCounter() {
        return productBoughtCounter;
    }

    public boolean isProductCounterBought() {
        return productBoughtCounter == 1;
    }

    public void buyProduct() {
        productBoughtCounter++;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}

