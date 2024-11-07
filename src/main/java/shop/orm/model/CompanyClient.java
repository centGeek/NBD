package shop.orm.model;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

public class CompanyClient extends ClientType {
    private String companyName;

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
