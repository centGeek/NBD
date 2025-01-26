package shop.orm.model;
import lombok.Getter;
import java.time.LocalDate;


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

    public IndividualClient() {
    }

    @Override
    public String toString() {
        return "IndividualClient{" + "email='" + email + '\'' +
                ", birthDate=" + birthDate +
                '}';
    }
}
