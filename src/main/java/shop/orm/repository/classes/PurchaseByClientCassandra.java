package shop.orm.repository.classes;

import com.datastax.oss.driver.api.mapper.annotations.CqlName;
import com.datastax.oss.driver.api.mapper.annotations.Entity;
import com.datastax.oss.driver.api.mapper.annotations.PartitionKey;
import com.datastax.oss.driver.api.mapper.annotations.PropertyStrategy;
import com.datastax.oss.driver.api.mapper.entity.naming.GetterStyle;
import shop.orm.model.Client;
import shop.orm.model.Product;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity(defaultKeyspace = CassandraConsts.DEFAULT_NAMESPACE)
@CqlName(CassandraConsts.PURCHASES_BY_CLIENT_TABLE_NAME)
@PropertyStrategy(mutable = false, getterStyle = GetterStyle.JAVABEANS)
public class PurchaseByClientCassandra {

    @PartitionKey
    private UUID clientUUID;

    private UUID id;
//TODO ZACZNIJ
//    private List<(Pair<String, UUID>)>productsUUID =new ArrayList<>();

    public PurchaseByClientCassandra(UUID clientUUID, UUID id, List<UUID> productsUUID) {
        this.clientUUID = clientUUID;
        this.id = id;
//        this.productsUUID = productsUUID;
    }


}