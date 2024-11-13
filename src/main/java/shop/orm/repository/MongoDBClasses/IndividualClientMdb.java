package shop.orm.repository.MongoDBClasses;

import org.bson.codecs.pojo.annotations.BsonCreator;
import org.bson.codecs.pojo.annotations.BsonDiscriminator;
import org.bson.codecs.pojo.annotations.BsonProperty;

import java.util.Objects;

@BsonDiscriminator(key = "_clazz",value = "Individual")
public class IndividualClientMdb extends ClientTypeMdb {


    @BsonCreator
    public IndividualClientMdb(
            @BsonProperty("_id") String _id,
//            @BsonProperty("pesel") String pesel,
            @BsonProperty("email") String email,
            @BsonProperty("birthDate") String birthData) {
        super(_id);
        this.email = email;
        this.birthData = birthData;
    }

    @BsonProperty("email")
    private String email;


    @BsonProperty("birthData")
    private String birthData;


    public String getEmail() {
        return email;
    }

    public String getBirthData() {
        return birthData;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        IndividualClientMdb that = (IndividualClientMdb) o;
        return Objects.equals(getEmail(), that.getEmail()) && Objects.equals(getBirthData(), that.getBirthData());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getEmail(), getBirthData());
    }
}
