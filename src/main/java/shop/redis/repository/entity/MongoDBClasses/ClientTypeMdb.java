package shop.redis.repository.entity.MongoDBClasses;

import org.bson.codecs.pojo.annotations.BsonCreator;
import org.bson.codecs.pojo.annotations.BsonDiscriminator;
import org.bson.codecs.pojo.annotations.BsonProperty;

@BsonDiscriminator(key = "_clazz")
public abstract class ClientTypeMdb extends AbstractEntityMdb {

    @BsonCreator
    public ClientTypeMdb(
            @BsonProperty("_id") String _id,
            @BsonProperty("pesel") String pesel
    ) {
        super(_id);
        this.pesel = pesel;
    }
    @BsonProperty("pesel")
    private String pesel;

    public String getPesel() {
        return pesel;
    }
}



