package shop.orm.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.Getter;


@Getter
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = IndividualClient.class, name = "individual"),
        @JsonSubTypes.Type(value = CompanyClient.class, name = "company")
})
public abstract class ClientType {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("pesel")
    private String pesel;

    @JsonProperty("client")
    private Client client;

    public ClientType(String pesel) {
        this.pesel = pesel;
    }
    public ClientType(Long id, String pesel) {
        this.id = id;
        this.pesel = pesel;
    }


    public ClientType() {
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("ClientType{");
        sb.append("id=").append(id);
        sb.append(", pesel='").append(pesel).append('\'');
        sb.append(", client=").append(client);
        sb.append('}');
        return sb.toString();
    }
}
