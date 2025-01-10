//package shop.orm.repository.classes;
//
//
//import com.datastax.oss.driver.api.mapper.annotations.CqlName;
//import com.datastax.oss.driver.api.mapper.annotations.Entity;
//import com.datastax.oss.driver.api.mapper.annotations.PropertyStrategy;
//
//import java.time.LocalDate;
//import java.util.UUID;
//
//@Entity(defaultKeyspace = CassandraConsts.DEFAULT_NAMESPACE)
//@CqlName(CassandraConsts.CLIENT_TYPE_TABLE_NAME)
//@PropertyStrategy(mutable = false)
//public class IndividualClientCassandra extends ClientTypeCassandra {
//
//    private String email;
//    @CqlName(ClientTypeConsts.BIRTHDATE_STRING)
//    private LocalDate birthDate;
//
//    public IndividualClientCassandra(UUID entityId, String email, LocalDate birthDate, String pesel, String discriminator) {
//        super(entityId, "individual", pesel);
//        this.email = email;
//        this.birthDate = birthDate;
//    }
//
//    public String getEmail() {
//        return email;
//    }
//
//    public LocalDate getBirthDate() {
//        return birthDate;
//    }
//}
