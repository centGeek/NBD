package shop.orm.repository.classes;

import com.datastax.oss.driver.api.mapper.annotations.*;
import com.datastax.oss.driver.api.mapper.entity.naming.GetterStyle;
import shop.orm.model.Product;

import java.math.BigDecimal;
import java.util.UUID;


@Entity(defaultKeyspace = CassandraConsts.DEFAULT_NAMESPACE)
@CqlName(CassandraConsts.PRODUCT_TABLE_NAME)
@PropertyStrategy(mutable = false, getterStyle = GetterStyle.JAVABEANS)
public class ProductCassandra {

    @ClusteringColumn
    @CqlName(CassandraConsts.PRODUCT_ID_NAME)
    private UUID id;

    @PartitionKey
    @CqlName(CassandraConsts.PRODUCT_NAME)
    private String productName;

    @CqlName(CassandraConsts.PRODUCT_PRICE_NAME)
    private BigDecimal price;

    @CqlName(CassandraConsts.PRODUCT_BOUGHT_NAME)
    private boolean productBought;


    public ProductCassandra(String productName, UUID id, BigDecimal price, boolean productBought) {
        this.id = id;
        this.productName = productName;
        this.price = price;
        this.productBought = productBought;
    }

    public ProductCassandra(Product product) {
        this.productName = product.getProductName();
        this.price = product.getPrice();
        this.productBought = product.isProductBought();
        this.id = product.getId();
    }

    public static Product ProductCassandraToProductModel(ProductCassandra productCassandra) {
        Product product = new Product(productCassandra.getProductName(), productCassandra.getPrice(), productCassandra.isProductBought(), productCassandra.getId());
        return product;
    }
    public Product toProductModel() {
        return ProductCassandraToProductModel(this);
    }

    public UUID getId() {
        return id;
    }

    public String getProductName() {
        return productName;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public boolean isProductBought() {
        return productBought;
    }


}
