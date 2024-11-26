package shop.redis.repository.mongoEntity;


import com.fasterxml.jackson.annotation.JsonCreator;
import org.bson.codecs.pojo.annotations.BsonCreator;
import org.bson.codecs.pojo.annotations.BsonProperty;

import java.io.Serializable;

public class AbstractEntityMdb implements Serializable {
    @BsonCreator
    public AbstractEntityMdb(@BsonProperty("_id") String id) {
        this.entityId = id;
    }


    @BsonProperty("_id")
    private final String entityId;

    public String getEntityId() {
        return entityId;
    }
}
