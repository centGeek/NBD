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


    @Insert
    void insert(ProductCassandra productCassandra);


    @Query("SELECT COUNT(*) FROM " + CassandraConsts.PRODUCT_TABLE_NAME)
    long count();

    @Select
    PagingIterable<ProductCassandra> findByName(String string);

    @Select
    ProductCassandra findByNameAndId(String name, UUID id);


    @Update
    void update(ProductCassandra productCassandra);

    @Delete
    void delete(ProductCassandra productCassandra);

}
