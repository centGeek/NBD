package shop.orm.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Entity
@Access(AccessType.FIELD)
class CompanyClient extends ClientType {
    @Column(name = "companyName")
    private String companyName;

    @Column(name = "NIP")
    private long NIP;

    public CompanyClient(String pesel, long NIP, String companyName) {
        super(pesel);
        this.NIP = NIP;
        this.companyName  = companyName;
    }

    public CompanyClient() {

    }
}
