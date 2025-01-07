package shop.orm.repository.classes;

import com.datastax.oss.driver.api.mapper.annotations.*;
import com.datastax.oss.driver.api.mapper.entity.naming.GetterStyle;
import com.datastax.oss.driver.api.mapper.entity.naming.NamingConvention;
import lombok.Getter;
import shop.orm.model.Address;

import java.util.UUID;



@Entity(defaultKeyspace = CassandraConsts.DEFAULT_NAMESPACE)
@PropertyStrategy(mutable = false, getterStyle = GetterStyle.JAVABEANS)
@CqlName(CassandraConsts.ADDRESS_TABLE_NAME)  //Kluczowe jezeli jednak klasa nazywa sie inaczej niz bysmy chcieli tabele.
public class AddressCassandra {

    public AddressCassandra(UUID id, String city, String country, String postal_code, String street, String street_number) {
        this.id = id;
        this.city = city;
        this.country = country;
        this.postal_code = postal_code;
        this.street = street;
        this.street_number = street_number;
    }


    public AddressCassandra(Address address) {
        this.id = address.getId();
        this.city = address.getCity();
        this.country = address.getCountry();
        this.postal_code = address.getPostal_code();
        this.street = address.getStreet();
        this.street_number = address.getStreet_number();
    }
    @PartitionKey
    private UUID id;

    @ClusteringColumn(0)
    private String country;

    @ClusteringColumn(1)
    private String city;

    private String street;

    private String postal_code;

    private String street_number;

    public static Address AddressCassandraToAddress(AddressCassandra addressCassandra){
        Address address = new Address(addressCassandra.getId(),addressCassandra.getCity(),addressCassandra.getCountry(),
                addressCassandra.getPostal_code(),addressCassandra.getStreet(),addressCassandra.getStreet_number());
        return address;
    }

    public Address toAddress(){
        return AddressCassandraToAddress(this);
    }

    public String getCountry() {
        return country;
    }

    public String getStreet() {
        return street;
    }

    public UUID getId() {
        return id;
    }

    public String getCity() {
        return city;
    }

    public String getPostal_code() {
        return postal_code;
    }

    public String getStreet_number() {
        return street_number;
    }
}
