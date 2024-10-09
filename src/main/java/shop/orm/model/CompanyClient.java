package shop.orm.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Access(AccessType.FIELD)
public class CompanyClient extends ClientType {
    @Column(name = "name")
    private String name;
    @Column(name = "NIP")
    private long NIP;

}
