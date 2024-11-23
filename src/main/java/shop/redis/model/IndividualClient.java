package shop.redis.model;


import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;



@Getter
@NoArgsConstructor
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
}
