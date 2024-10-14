package shop.orm.model;

import jakarta.persistence.Access;
import jakarta.persistence.AccessType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;


@Setter
@Getter
@Entity
@Access(AccessType.FIELD)
public class IndividualClient extends ClientType {
    @Column(name = "email")
    private String email;
    @Column(name = "birth_date")
    private LocalDate birthDate;

    public IndividualClient(String pesel, String email, LocalDate birthDate) {
        super(pesel);
        this.email = email;
        this.birthDate = birthDate;
    }

    public IndividualClient() {

    }

    @Override
    public String toString() {
        return "IndividualClient{" + "email='" + email + '\'' +
                ", birthDate=" + birthDate +
                '}';
    }
}
