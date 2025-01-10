//package shop.orm.repository.dao;
//
//import com.datastax.oss.driver.api.mapper.annotations.Dao;
//import com.datastax.oss.driver.api.mapper.annotations.Delete;
//import com.datastax.oss.driver.api.mapper.annotations.QueryProvider;
//import com.datastax.oss.driver.api.mapper.annotations.StatementAttributes;
//import shop.orm.repository.classes.AddressCassandra;
//import shop.orm.repository.classes.ClientTypeCassandra;
//import shop.orm.repository.classes.CompanyCassandra;
//import shop.orm.repository.classes.IndividualClientCassandra;
//import shop.orm.repository.classes.providers.ClientTypeProvider;
//
//
//@Dao
//public interface ClientTypeDao {
//
//    @StatementAttributes(consistencyLevel = "QUORUM")
//    @QueryProvider(providerClass = ClientTypeProvider.class,
//            entityHelpers = {IndividualClientCassandra.class, CompanyCassandra.class})
//    void create(ClientTypeCassandra clientTypeCassandra);
//
//    @Delete
//    void delete(ClientTypeCassandra clientTypeCassandra);
//
//}
