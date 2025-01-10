//package shop.orm.repository.classes.providers;
//
//import com.datastax.oss.driver.api.core.CqlSession;
//import com.datastax.oss.driver.api.mapper.MapperContext;
//import com.datastax.oss.driver.api.mapper.entity.EntityHelper;
//import shop.orm.repository.classes.ClientCassandra;
//import shop.orm.repository.classes.ClientTypeCassandra;
//import shop.orm.repository.classes.CompanyCassandra;
//import shop.orm.repository.classes.IndividualClientCassandra;
//
//public class ClientInsertProvider {
//    private final ClientTypeProvider clientTypeProvider;
//    private final EntityHelper<ClientCassandra> clientEntityHelper;
//    private final CqlSession cassandraSession;
//
//    public ClientInsertProvider(MapperContext context, EntityHelper<ClientCassandra> clientEntityHelper, EntityHelper<IndividualClientCassandra> clientCassandraEntityHelper, EntityHelper<CompanyCassandra> companyCassandraEntityHelper) {
//        this.clientTypeProvider = new ClientTypeProvider(context, clientCassandraEntityHelper, companyCassandraEntityHelper);
//        this.clientEntityHelper = clientEntityHelper;
//        this.cassandraSession = context.getSession();
//    }
//
//    public void insertWithClientType(ClientCassandra clientCassandra) {
//        // Insert ClientType first
//        clientTypeProvider.create(clientCassandra.getClientTypeCassandra());
//        // Insert Client
//
////        cassandraSession.execute(clientEntityHelper.insert().build().bind()
////                .setUuid("entity_id", clientCassandra.getEntityId())
////                .set("client_type", clientCassandra.getClientType(), ClientTypeCassandra.class));
//    }
//}
