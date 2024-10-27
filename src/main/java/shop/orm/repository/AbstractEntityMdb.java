package shop.orm.repository;


import org.bson.codecs.pojo.annotations.BsonCreator;
import org.bson.codecs.pojo.annotations.BsonProperty;

import java.io.Serializable;

public abstract class AbstractEntityMdb implements Serializable {
    @BsonCreator
    public AbstractEntityMdb(long id) {
        this.entityId = id;
    }


    @BsonProperty("_id")
    private final long entityId;

    public long getEntityId() {
        return entityId;
    }

}
