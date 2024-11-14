package shop.orm.repository.MongoDBClasses;

import org.bson.codecs.pojo.annotations.BsonCreator;
import org.bson.codecs.pojo.annotations.BsonProperty;
import shop.orm.model.Address;

import java.util.UUID;

public class AddressMdb extends AbstractEntityMdb {
    @BsonProperty
    private String city;
    @BsonProperty
    private String country;
    @BsonProperty
    private String postal_code;
    @BsonProperty
    private String street;
    @BsonProperty
    private String street_number;

    @BsonCreator
    public AddressMdb(
            @BsonProperty("_id") String id,
            @BsonProperty("city") String city,
            @BsonProperty("country") String country,
            @BsonProperty("postal_code") String postal_code,
            @BsonProperty("street") String street,
            @BsonProperty("street_number") String street_number) {
        super(id);
        this.city = city;
        this.country = country;
        this.postal_code = postal_code;
        this.street = street;
        this.street_number = street_number;
    }


    public AddressMdb(Address address, String uuid) {
        super(uuid);
        this.city = address.getCity();
        this.country = address.getCountry();
        this.postal_code = address.getPostal_code();
        this.street = address.getStreet();
        this.street_number = address.getStreet_number();
    }

    public static Address AddresMdbToAddress(AddressMdb addressMdb) {
        return new Address(addressMdb.getCity(), addressMdb.getCountry(), addressMdb.getPostal_code(), addressMdb.getStreet(), addressMdb.getStreet_number());
    }

    public String getCity() {
        return city;
    }

    public String getCountry() {
        return country;
    }

    public String getPostal_code() {
        return postal_code;
    }

    public String getStreet() {
        return street;
    }

    public String getStreet_number() {
        return street_number;
    }
}
