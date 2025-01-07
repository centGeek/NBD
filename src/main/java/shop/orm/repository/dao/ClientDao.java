package shop.orm.repository.dao;

import com.datastax.oss.driver.api.mapper.annotations.*;
import shop.orm.model.Client;
import shop.orm.repository.classes.CassandraConsts;
import shop.orm.repository.classes.ClientCassandra;

import com.datastax.oss.driver.api.core.cql.ResultSet;
import java.util.List;

@Dao
public interface ClientDao {
    @Insert
    void insert(ClientCassandra clientCassandra);


//    @Query("SELECT * FROM " + CassandraConsts.CLIENT_TABLE_NAME + " WHERE pesel = :pesel")
//    ClientCassandra findByPesel(String pesel);

    @Query("SELECT COUNT(*) FROM " + CassandraConsts.CLIENT_TABLE_NAME)
    long count();


    @Delete
    void delete(ClientCassandra clientCassandra);

//    @Query("SELECT * FROM " + CassandraConsts.CLIENT_TABLE_NAME)
//    ResultSet getAllClients();
}
