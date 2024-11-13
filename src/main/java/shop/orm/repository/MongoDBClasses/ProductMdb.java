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
            @BsonProperty("isProductBought") int isProductBought
    ) {
        super(id);
        this.productName = productName;
        this.price = price;
        this.isProductBought = isProductBought;
    }

    public ProductMdb(Product product) {
        super(product.getId().toString());
        this.productName = product.getProductName();
        this.price = product.getPrice();
        this.isProductBought = product.isProductBought() ? 1 : 0;
    }

    @BsonProperty("productName")
    private String productName;
    @BsonProperty("price")
    private BigDecimal price;
    @BsonProperty("isProductBought")
    private int isProductBought;


    public static Product productFromProductMdb(ProductMdb productMdb) {
        return new Product(productMdb.getProductName(), productMdb.getPrice(), productMdb.isProductBought(), UUID.fromString(productMdb.getEntityId()));
    }

    public String getProductName() {
        return productName;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public int getIsProductBought() {
        return isProductBought;
    }

    public boolean isProductBought() {
        return isProductBought == 1;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}

