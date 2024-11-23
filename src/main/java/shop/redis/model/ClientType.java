package shop.redis.model;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@NoArgsConstructor
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "clientType")
@JsonSubTypes({
        @JsonSubTypes.Type(value = IndividualClient.class, name = "individualClient"),
        @JsonSubTypes.Type(value = CompanyClient.class, name = "companyClient")
})
public abstract class ClientType {

    private Long id;

    private String pesel;
    private Client client;

    public ClientType(String pesel) {
        this.pesel = pesel;
    }
}
