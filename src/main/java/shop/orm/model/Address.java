package shop.orm.model;

import lombok.*;


@EqualsAndHashCode
@Getter
public class Address {

    public Address(String city, String country, String postal_code, String street, String street_number) {
        this.city = city;
        this.country = country;
        this.postal_code = postal_code;
        this.street = street;
        this.street_number = street_number;
    }


    private Integer addressId;

    private String city;

    private String country;

    private String postal_code;

    private String street;

    private String street_number;


    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Address{");
        sb.append("address_id=").append(addressId);
        sb.append(", city='").append(city).append('\'');
        sb.append(", country='").append(country).append('\'');
        sb.append(", postal_code='").append(postal_code).append('\'');
        sb.append(", street='").append(street).append('\'');
        sb.append(", street_number='").append(street_number).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
