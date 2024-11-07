package shop.orm.repository.MongoDBClasses;

import org.bson.codecs.pojo.annotations.BsonDiscriminator;
import org.bson.codecs.pojo.annotations.BsonProperty;

@BsonDiscriminator(key = "_t",value = "Individual")
public class IndividualClientMdb implements ClientTypeMdb {

    public IndividualClientMdb(String email, String birthData) {
        this.email = email;
        this.birthData = birthData;
    }

    @BsonProperty("email")
    private String email;


    @BsonProperty("birthData")
    private String birthData;
}
