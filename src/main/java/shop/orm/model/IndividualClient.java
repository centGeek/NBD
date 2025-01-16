package shop.orm.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Objects;


@Getter

public class IndividualClient extends ClientType {
    @Getter
    private String email;
    @Getter
    private LocalDate birthDate;

    public IndividualClient(String pesel, String email, LocalDate birthDate) {
        super(pesel);
        this.email = email;
        this.birthDate = birthDate;
    }


    @Override
    public String toString() {
        return "IndividualClient{" + "email='" + email + '\'' +
                ", birthDate=" + birthDate +
                '}';
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        IndividualClient that = (IndividualClient) o;
        return Objects.equals(getEmail(), that.getEmail()) && Objects.equals(getBirthDate(), that.getBirthDate());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getEmail(), getBirthDate());
    }
}
