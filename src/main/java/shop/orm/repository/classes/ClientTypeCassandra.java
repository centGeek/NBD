package shop.orm.repository.classes;


import com.datastax.oss.driver.api.mapper.annotations.CqlName;
import com.datastax.oss.driver.api.mapper.annotations.Entity;
import com.datastax.oss.driver.api.mapper.annotations.PropertyStrategy;

import java.util.UUID;

//@Entity(defaultKeyspace = CassandraConsts.DEFAULT_NAMESPACE)
@PropertyStrategy(mutable = false)
@CqlName(CassandraConsts.CLIENT_TYPE_TABLE_NAME)
public class ClientTypeCassandra extends AbstractEntity {

    private String pesel;

    private String discriminator;

    public ClientTypeCassandra(UUID entityId, String discriminator, String pesel) {
        super(entityId);
        this.pesel = pesel;
        this.discriminator = discriminator;
    }

    public String getPesel() {
        return pesel;
    }

    public String getDiscriminator() {
        return discriminator;
    }
}
