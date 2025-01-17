package shop.orm.repository.dao;

import com.datastax.oss.driver.api.core.PagingIterable;
import com.datastax.oss.driver.api.mapper.annotations.*;
import shop.orm.repository.classes.CassandraConsts;
import shop.orm.repository.classes.ClientCassandra;
import shop.orm.repository.classes.ProductCassandra;

import java.math.BigDecimal;
import java.util.UUID;

@Dao
public interface ProductDao {


    @StatementAttributes(consistencyLevel = "QUORUM")
    @Insert
    void insert(ProductCassandra productCassandra);


    @StatementAttributes(consistencyLevel = "QUORUM")
    @Query("SELECT COUNT(*) FROM " + CassandraConsts.PRODUCT_TABLE_NAME)
    long count();

    @StatementAttributes(consistencyLevel = "QUORUM")
    @Select
    PagingIterable<ProductCassandra> findByName(String string);

    @StatementAttributes(consistencyLevel = "QUORUM")
    @Select
    ProductCassandra findByNameAndId(String name, UUID id);


    @StatementAttributes(consistencyLevel = "QUORUM")
    @Update
    void update(ProductCassandra productCassandra);

    @StatementAttributes(consistencyLevel = "QUORUM")
    @Delete
    void delete(ProductCassandra productCassandra);

}
