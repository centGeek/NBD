//package shop.orm.repository;
//
//import com.datastax.oss.driver.api.core.CqlIdentifier;
//import com.datastax.oss.driver.api.core.CqlSession;
//import com.datastax.oss.driver.api.core.cql.SimpleStatement;
//import com.datastax.oss.driver.api.core.metadata.schema.ClusteringOrder;
//import com.datastax.oss.driver.api.core.type.DataTypes;
//import com.datastax.oss.driver.api.querybuilder.QueryBuilder;
//import com.datastax.oss.driver.api.querybuilder.SchemaBuilder;
//import com.datastax.oss.driver.api.querybuilder.insert.Insert;
//import org.junit.jupiter.api.Test;
//import shop.orm.menagers.TestData;
//import shop.orm.model.Address;
//import shop.orm.repository.classes.AddressCassandra;
//import shop.orm.repository.classes.CassandraConsts;
//import shop.orm.repository.dao.AddressDao;
//import shop.orm.repository.mapper.AddressMapper;
//import shop.orm.repository.mapper.AddressMapperBuilder;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//class TestImplementation extends AbstractCassandraRepository {
//
//    CqlSession session;
//    String tableName = "testTable";
//
//    TestImplementation() {
//        session = AbstractCassandraRepository.getDatabase();
//    }
//
//    void createTestTable() {
//
//        SimpleStatement dropTable = SchemaBuilder.dropTable(CqlIdentifier.fromCql(tableName)).ifExists().build();
//        session.execute(dropTable);
//
//        SimpleStatement crateTestTable = SchemaBuilder.createTable(CqlIdentifier.fromCql(tableName)).ifNotExists()
//                .withPartitionKey(CqlIdentifier.fromCql("login"), DataTypes.TEXT)
//                .withClusteringColumn(CqlIdentifier.fromCql("type"), DataTypes.TEXT)
//                .withClusteringOrder(CqlIdentifier.fromCql("type"), ClusteringOrder.ASC)
//                .build();
//        session.execute(crateTestTable);
//    }
//
//    void tryInsert() {
//        Insert insert = QueryBuilder.insertInto(tableName)
//                .value("login", QueryBuilder.literal("testLogin"))
//                .value("type", QueryBuilder.literal("testType"));
//        SimpleStatement statement = insert.build();
//        session.execute(statement);
//    }
//
//
//    void insertOneAddress(Address address) {
//
//
//        SimpleStatement dropTable = SchemaBuilder.dropTable(CqlIdentifier.fromCql(CassandraConsts.ADDRESS_TABLE_NAME)).ifExists().build();
//        session.execute(dropTable);
//
//        SimpleStatement crateTestTable = SchemaBuilder.createTable(CqlIdentifier.fromCql(CassandraConsts.ADDRESS_TABLE_NAME)).ifNotExists()
//                .withPartitionKey(CqlIdentifier.fromCql("id"), DataTypes.UUID)
//                .withClusteringColumn(CqlIdentifier.fromCql("country"), DataTypes.TEXT)
//                .withClusteringColumn(CqlIdentifier.fromCql("city"), DataTypes.TEXT)
//                .withColumn(CqlIdentifier.fromCql("street"), DataTypes.TEXT)
//                .withColumn(CqlIdentifier.fromCql("postal_code"), DataTypes.TEXT)
//                .withColumn(CqlIdentifier.fromCql("street_number"), DataTypes.TEXT)
//                .withClusteringOrder(CqlIdentifier.fromCql("country"), ClusteringOrder.ASC)
//                .withClusteringOrder(CqlIdentifier.fromCql("city"), ClusteringOrder.ASC)
//                .build();
//        session.execute(crateTestTable);
//
//        AddressMapper addressMapper = new AddressMapperBuilder(session).build();
//        AddressDao addressDao = addressMapper.addressDao();
//        AddressCassandra addressCassandra = new AddressCassandra(address);
//        addressDao.create(addressCassandra);
//    }
//}
//
//class AbstractCassandraRepositoryTest {
//
//    @Test
//    void getDatabaseTest() {
//        try (TestImplementation repo = new TestImplementation()) {
//            repo.createTestTable();
//            assertTrue(true);
//            repo.tryInsert();
//            assertTrue(true);
//            repo.insertOneAddress(TestData.getClient1().getAddress());
//            assertTrue(true);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//}