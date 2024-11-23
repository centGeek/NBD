package shop.redis.model;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class CompanyClient extends ClientType {

    @Getter
    private String companyName;

    @Getter
    private long NIP;

    public CompanyClient(String pesel, long NIP, String companyName) {
        super(pesel);
        this.NIP = NIP;
        this.companyName = companyName;
    }


    @Override
    public String toString() {
        return "CompanyClient{" + "companyName='" + companyName + '\'' +
                ", NIP=" + NIP +
                '}';
    }


}
