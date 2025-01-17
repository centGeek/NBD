package shop.orm.repository.classes;

import com.datastax.oss.driver.api.mapper.annotations.*;
import com.datastax.oss.driver.api.mapper.entity.naming.GetterStyle;
import shop.orm.model.Purchase;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Entity(defaultKeyspace = CassandraConsts.DEFAULT_NAMESPACE)
@CqlName(CassandraConsts.PURCHASE_BY_PRODUCT_NAME)
@PropertyStrategy(mutable = false, getterStyle = GetterStyle.JAVABEANS)
public class PurchaseByProductCassandra {


    @CqlName(CassandraConsts.PURCHASE_BY_PRODUCT_PRODUCT_NAME)
    @PartitionKey
    private String productName;

    @ClusteringColumn
    @CqlName(CassandraConsts.PURCHASE_BY_PRODUCT_ID_NAME)
    private UUID productId;

    @CqlName(CassandraConsts.PURCHASE_BY_CLIENT_PRODUCTS_NAME)
    private UUID clientId;

    @CqlName(CassandraConsts.PURCHASE_ID_NAME)
    private UUID purchaseId;

    public PurchaseByProductCassandra(String productName, UUID productId, UUID clientId, UUID purchaseId) {
        this.productName = productName;
        this.productId = productId;
        this.clientId = clientId;
        this.purchaseId = purchaseId;
    }




    public String getProductName() {
        return productName;
    }

    public UUID getProductId() {
        return productId;
    }

    public UUID getClientId() {
        return clientId;
    }

    public UUID getPurchaseId() {
        return purchaseId;
    }
}
