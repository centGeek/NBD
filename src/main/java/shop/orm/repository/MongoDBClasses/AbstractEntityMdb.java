package shop.orm.repository.MongoDBClasses;


import org.bson.codecs.pojo.annotations.BsonCreator;
import org.bson.codecs.pojo.annotations.BsonProperty;

import java.io.Serializable;
import java.util.UUID;

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
