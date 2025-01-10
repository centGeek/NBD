//package shop.orm.repository.classes.providers;
//
//import com.datastax.oss.driver.api.core.CqlSession;
//import com.datastax.oss.driver.api.mapper.MapperContext;
//import com.datastax.oss.driver.api.mapper.entity.EntityHelper;
//import shop.orm.repository.classes.ClientTypeCassandra;
//import shop.orm.repository.classes.ClientTypeConsts;
//import shop.orm.repository.classes.CompanyCassandra;
//import shop.orm.repository.classes.IndividualClientCassandra;
//
//public class ClientTypeProvider {
//    private final CqlSession session;
//
//    private EntityHelper<IndividualClientCassandra> clientCassandraEntityHelper;
//    private EntityHelper<CompanyCassandra> clientTypeCassandraEntityHelper;
//
//    public ClientTypeProvider(MapperContext ctx, EntityHelper<IndividualClientCassandra> clientCassandraEntityHelper, EntityHelper<CompanyCassandra> companyCassandraEntityHelper) {
//        this.session = ctx.getSession();
//        this.clientCassandraEntityHelper = clientCassandraEntityHelper;
//        this.clientTypeCassandraEntityHelper = companyCassandraEntityHelper;
//    }
//
//    public void create(ClientTypeCassandra clientTypeCassandra) {
//        session.execute(
//            switch (clientTypeCassandra.getDiscriminator()){
//                case "individual" -> {
//                    IndividualClientCassandra individualClientCassandra = (IndividualClientCassandra) clientTypeCassandra;
//                    yield session.prepare(clientCassandraEntityHelper.insert().build())
//                            .bind()
//                            .setUuid("entity_id",individualClientCassandra.getEntityId())
//                            .setString(ClientTypeConsts.DISCRIMINATOR_STRING,individualClientCassandra.getDiscriminator())
//                            .setString(ClientTypeConsts.PESEL_CQL_STRING,individualClientCassandra.getPesel())
//                            .setString(ClientTypeConsts.EMAIL_STRING, individualClientCassandra.getEmail())
//                            .setLocalDate(ClientTypeConsts.BIRTHDATE_STRING, individualClientCassandra.getBirthDate());
//
//                }
//                default ->
//                        throw new IllegalStateException("Unexpected value: " + clientTypeCassandra.getDiscriminator());
//            }
//
//
//
//        );
//    }
//
//
//
//}
//
