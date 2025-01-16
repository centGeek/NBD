package shop.orm.repository.dao;

import com.datastax.oss.driver.api.core.PagingIterable;
import com.datastax.oss.driver.api.mapper.annotations.*;
import shop.orm.model.Client;
import shop.orm.repository.classes.CassandraConsts;
import shop.orm.repository.classes.ClientCassandra;

import com.datastax.oss.driver.api.core.cql.ResultSet;


import java.util.List;
import java.util.UUID;

@Dao
public interface ClientDao {
    @Insert
    void insert(ClientCassandra clientCassandra);


    @Query("SELECT COUNT(*) FROM " + CassandraConsts.CLIENT_TABLE_NAME)
    long count();

    @Select
    ClientCassandra findById(UUID id);

    @Delete
    void delete(ClientCassandra clientCassandra);

    @Update
    void update(ClientCassandra clientCassandra);

    @Select/*("SELECT * FROM " + CassandraConsts.CLIENT_TABLE_NAME)*/
    PagingIterable<ClientCassandra> getAllClients();

//    @QueryProvider(providerClass = ClientInsertProvider.class, entityHelpers = {ClientCassandra.class, IndividualClientCassandra.class, CompanyCassandra.class})
//    void insertWithClientType(ClientCassandra clientCassandra);
}
