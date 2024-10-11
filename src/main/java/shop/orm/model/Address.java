package shop.orm.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Embeddable
@Access(AccessType.FIELD)
public class Address {
    @Id
    private Integer address_id;

    @Column(name = "city")
    private String city;

    @Column(name = "country")
    private String country;

    @Column(name = "postal_code")
    private String postal_code;

    @Column(name = "street")
    private String street;

    @Column(name = "street_number")
    private String street_number;

    @OneToOne(mappedBy = "address")
    private ClientType clientType;

}
