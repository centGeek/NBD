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
        final StringBuilder sb = new StringBuilder("IndividualClient{");
        sb.append("pesel='").append(getPesel()).append('\'');
        sb.append("email='").append(email).append('\'');
        sb.append(", birthDate=").append(birthDate);
        sb.append('}');
        return sb.toString();
    }
}
