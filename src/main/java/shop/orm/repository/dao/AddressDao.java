package shop.orm.repository.dao;

import com.datastax.oss.driver.api.mapper.annotations.Dao;
import com.datastax.oss.driver.api.mapper.annotations.Insert;
import com.datastax.oss.driver.api.mapper.annotations.Query;
import shop.orm.repository.classes.AddressCassandra;
import shop.orm.repository.classes.CassandraConsts;

import java.util.UUID;

@Dao
public interface AddressDao {
    @Insert
    void create(AddressCassandra addressCassandra);

//    @Query("SELECT * FROM " + CassandraConsts.ADDRESS_TABLE_NAME + " WHERE id = :id")
//    AddressCassandra findById(UUID id);
}
