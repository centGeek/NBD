package shop.orm.repository;

import com.datastax.oss.driver.api.core.CqlIdentifier;
import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.cql.SimpleStatement;
import com.datastax.oss.driver.api.core.metadata.schema.ClusteringOrder;
import com.datastax.oss.driver.api.core.type.DataType;
import com.datastax.oss.driver.api.core.type.DataTypes;

import com.datastax.oss.driver.api.querybuilder.QueryBuilder;
import com.datastax.oss.driver.api.querybuilder.SchemaBuilder;
import com.datastax.oss.driver.api.querybuilder.insert.Insert;
import com.datastax.oss.driver.api.querybuilder.schema.CreateKeyspace;
import org.junit.jupiter.api.Test;

import static com.datastax.oss.driver.api.querybuilder.SchemaBuilder.createKeyspace;
import static org.junit.jupiter.api.Assertions.*;

class TestImplementation extends AbstractCassandraRepository {

    CqlSession session;
    String tableName = "testTable";

    TestImplementation() {
        session =  AbstractCassandraRepository.getDatabase();
    }

    void createTestTable() {

        SimpleStatement dropTable = SchemaBuilder.dropTable(CqlIdentifier.fromCql(tableName)).ifExists().build();
        session.execute(dropTable);

        SimpleStatement crateTestTable = SchemaBuilder.createTable(CqlIdentifier.fromCql(tableName)).ifNotExists()
        .withPartitionKey(CqlIdentifier.fromCql("login"), DataTypes.TEXT)
                .withClusteringColumn(CqlIdentifier.fromCql("type"), DataTypes.TEXT)
                .withClusteringOrder(CqlIdentifier.fromCql("type"), ClusteringOrder.ASC)
                .build();
        session.execute(crateTestTable);
    }

    void tryInsert(){
        Insert insert = QueryBuilder.insertInto(tableName)
                .value("login", QueryBuilder.literal("testLogin"))
                .value("type", QueryBuilder.literal("testType"));
        SimpleStatement statement = insert.build();
        session.execute(statement);
    }
}

class AbstractCassandraRepositoryTest {




    @Test
    void getDatabaseTest() {
        try(TestImplementation repo = new TestImplementation())
        {
            repo.createTestTable();
            repo.tryInsert();
            assertTrue(true);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}