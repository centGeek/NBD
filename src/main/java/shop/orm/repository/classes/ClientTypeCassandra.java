package shop.orm.repository.classes;


import com.datastax.oss.driver.api.mapper.annotations.CqlName;
import com.datastax.oss.driver.api.mapper.annotations.Entity;
import com.datastax.oss.driver.api.mapper.annotations.PropertyStrategy;
import shop.orm.model.ClientType;
import shop.orm.model.CompanyClient;
import shop.orm.model.IndividualClient;

import java.time.LocalDate;
import java.util.UUID;

@Entity(defaultKeyspace = CassandraConsts.DEFAULT_NAMESPACE)
@PropertyStrategy(mutable = false)
@CqlName(CassandraConsts.CLIENT_TYPE_TABLE_NAME)
public class ClientTypeCassandra extends AbstractEntity {

    private String pesel;

    private String discriminator;

    private String email;
    @CqlName(ClientTypeConsts.BIRTHDATE_STRING)
    private LocalDate birthDate;

    @CqlName(ClientTypeConsts.COMPANY_NAME_STRING)
    private String companyName;

    private long nip;


    public ClientTypeCassandra(UUID entityId, String pesel, String discriminator, String email, LocalDate birthDate, String companyName, Long nip) {
        super(entityId);
        this.pesel = pesel;
        this.discriminator = discriminator;
        this.email = email;
        this.birthDate = birthDate;
        this.companyName = companyName;
        this.nip = nip;
    }

    public String getPesel() {
        return pesel;
    }

    public String getDiscriminator() {
        return discriminator;
    }

    public String getEmail() {
        return email;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public String getCompanyName() {
        return companyName;
    }

    public long getNip() {
        return nip;
    }

    public static ClientType ClientTypeCassandraToClientType(ClientTypeCassandra clientTypeCassandra) {
        switch (clientTypeCassandra.getDiscriminator()) {
            case "individual" -> {
                IndividualClient individualClient = new IndividualClient(clientTypeCassandra.getPesel(), clientTypeCassandra.getEmail(), clientTypeCassandra.getBirthDate());
                return individualClient;
            }
            case "company" -> {
                CompanyClient companyClient = new CompanyClient(clientTypeCassandra.getPesel(), clientTypeCassandra.getNip(), clientTypeCassandra.companyName);
                return companyClient;
            }
            default -> {
                throw new IllegalArgumentException("Invalid discriminator: " + clientTypeCassandra.getDiscriminator());
            }
        }
    }

    public ClientType toClientType() {
        return ClientTypeCassandraToClientType(this);
    }

}
