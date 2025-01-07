//package shop.orm.repository.classes;
//
//import com.datastax.oss.driver.api.mapper.annotations.CqlName;
//import com.datastax.oss.driver.api.mapper.annotations.Entity;
//import com.datastax.oss.driver.api.mapper.annotations.PropertyStrategy;
//import com.datastax.oss.driver.api.mapper.entity.naming.GetterStyle;
//import lombok.Getter;
//import lombok.Setter;
//import shop.orm.model.Address;
//import shop.orm.model.ClientType;
//
//import java.util.UUID;
//
//
//@Entity(defaultKeyspace = CassandraConsts.DEFAULT_NAMESPACE)
//@CqlName(CassandraConsts.CLIENT_TABLE_NAME)
//@PropertyStrategy(mutable = false ,getterStyle = GetterStyle.JAVABEANS)
//public class ClientCassandra {
//
//
//    @Getter
//    private UUID id;
//    @Setter
//    @Getter
//    private Address address;
//    @Getter
//    private ClientType clientType;
//
//
//}