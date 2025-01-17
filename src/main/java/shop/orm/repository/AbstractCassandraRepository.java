package shop.orm.repository;


import com.datastax.oss.driver.api.core.CqlIdentifier;
import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.cql.SimpleStatement;
import com.datastax.oss.driver.api.querybuilder.schema.CreateKeyspace;

import java.net.InetSocketAddress;

import static com.datastax.oss.driver.api.querybuilder.SchemaBuilder.createKeyspace;

public abstract class AbstractCassandraRepository implements AutoCloseable {

    protected static int closedCounter = 0;

    public AbstractCassandraRepository() {
    }


    private static CqlSession session;


    public static CqlSession getDatabase() {
        closedCounter++;
        if (session == null) {

            try{
                session = CqlSession.builder()
                        .addContactPoint(new InetSocketAddress("cassandra1", 9042))
                        .addContactPoint(new InetSocketAddress("cassandra2", 9043))
                        .withLocalDatacenter("dc1")
                        .withAuthCredentials("admin", "admin")
                        .withKeyspace("shop")
                        .build();
            }catch (Exception e){
                session = CqlSession.builder()
                        .addContactPoint(new InetSocketAddress("cassandra1", 9042))
                        .addContactPoint(new InetSocketAddress("cassandra2", 9043))
                        .withLocalDatacenter("dc1")
                        .withAuthCredentials("admin", "admin")
                        //.withKeyspace("shop")
                        .build();
                CreateKeyspace keyspace = createKeyspace(CqlIdentifier.fromCql("shop"))
                        .ifNotExists()
                        .withSimpleStrategy(2)
                        .withDurableWrites(true);
                SimpleStatement createKeyspace = keyspace.build();
                session.execute(createKeyspace);

                session = CqlSession.builder()
                        .addContactPoint(new InetSocketAddress("cassandra1", 9042))
                        .addContactPoint(new InetSocketAddress("cassandra2", 9043))
                        .withLocalDatacenter("dc1")
                        .withAuthCredentials("admin", "admin")
                        .withKeyspace("shop")
                        .build();
            }
        }
        return session;
    }

    public static void decrementCounter() {
        closedCounter--;
        if (closedCounter == 0 && session != null) {
            session.close();
            session = null;
        }
    }

    @Override
    public void close() throws Exception {
        decrementCounter();
    }

}
