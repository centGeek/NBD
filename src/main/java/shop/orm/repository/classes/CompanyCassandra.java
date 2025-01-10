//package shop.orm.repository.classes;
//
//
//import com.datastax.oss.driver.api.mapper.annotations.CqlName;
//import com.datastax.oss.driver.api.mapper.annotations.Entity;
//import com.datastax.oss.driver.api.mapper.annotations.PropertyStrategy;
//
//import java.util.UUID;
//
//@Entity(defaultKeyspace = CassandraConsts.DEFAULT_NAMESPACE)
//@CqlName(CassandraConsts.CLIENT_TYPE_TABLE_NAME)
//@PropertyStrategy(mutable = false)
//public class CompanyCassandra extends ClientTypeCassandra{
//    @CqlName(ClientTypeConsts.COMPANY_NAME_STRING)
//    private String companyName;
//
//    private long nip;
//
//    public CompanyCassandra(UUID entityId, String companyName, long nip, String pesel , String discriminator ) {
//        super(entityId, discriminator, pesel);
//        this.companyName = companyName;
//        this.nip = nip;
//    }
//
//    public String getCompanyName() {
//        return companyName;
//    }
//
//    public long getNip() {
//        return nip;
//    }
//}
