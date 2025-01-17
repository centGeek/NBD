package shop.orm.repository.dao;

import com.datastax.oss.driver.api.mapper.annotations.*;
import shop.orm.model.Purchase;
import shop.orm.repository.classes.PurchaseByProductCassandra;

import java.util.UUID;

@Dao
public interface PurchaseByProductDao {
    @StatementAttributes(consistencyLevel = "QUORUM")
    @Insert
    void insert(PurchaseByProductCassandra purchase);

    @StatementAttributes(consistencyLevel = "QUORUM")
    @Delete
    void delete(PurchaseByProductCassandra purchase);

    @StatementAttributes(consistencyLevel = "QUORUM")
    @Update
    void update(PurchaseByProductCassandra purchase);

    @StatementAttributes(consistencyLevel = "QUORUM")
    @Select
    PurchaseByProductCassandra selectById(String nameOfProduct, UUID productId);

}
