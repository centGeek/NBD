package shop.orm.model;

import lombok.*;

import java.util.Objects;
import java.util.UUID;



@Getter
public class Address {

    public Address(String city, String country, String postal_code, String street, String street_number) {
        this.id = UUID.randomUUID();
        this.city = city;
        this.country = country;
        this.postal_code = postal_code;
        this.street = street;
        this.street_number = street_number;
    }

    public Address() {
    }

    public Address(UUID id, String city, String country, String postal_code, String street, String street_number) {
        this.id = id;
        this.city = city;
        this.country = country;
        this.postal_code = postal_code;
        this.street = street;
        this.street_number = street_number;

    }

    @Getter
    private UUID id;

    private String city;

    private String country;

    private String postal_code;

    private String street;

    private String street_number;


    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Address{");
        sb.append(", city='").append(city).append('\'');
        sb.append(", country='").append(country).append('\'');
        sb.append(", postal_code='").append(postal_code).append('\'');
        sb.append(", street='").append(street).append('\'');
        sb.append(", street_number='").append(street_number).append('\'');
        sb.append('}');
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Address address = (Address) o;
        return Objects.equals(getCity(), address.getCity()) && Objects.equals(getCountry(), address.getCountry()) && Objects.equals(getPostal_code(), address.getPostal_code()) && Objects.equals(getStreet(), address.getStreet()) && Objects.equals(getStreet_number(), address.getStreet_number());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getCity(), getCountry(), getPostal_code(), getStreet(), getStreet_number());
    }
}
