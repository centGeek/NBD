package shop.orm.repository.dao;

import com.datastax.oss.driver.api.core.PagingIterable;
import com.datastax.oss.driver.api.mapper.annotations.*;
import shop.orm.repository.classes.ProductCassandra;
import shop.orm.repository.classes.PurchaseByClientCassandra;

import java.util.UUID;

@Dao
public interface PurchaseByClientDao {

    @StatementAttributes(consistencyLevel = "QUORUM")
    @Select
    PagingIterable<PurchaseByClientCassandra> findAllPurchasesOfClient(UUID clientUUID);

    @StatementAttributes(consistencyLevel = "QUORUM")
    @Select
    PurchaseByClientCassandra find(UUID clientUUID, UUID purchaseId);

    @StatementAttributes(consistencyLevel = "QUORUM")
    @Insert
    void insert(PurchaseByClientCassandra purchase);

    @StatementAttributes(consistencyLevel = "QUORUM")
    @Delete
    void delete(PurchaseByClientCassandra purchase);

}
