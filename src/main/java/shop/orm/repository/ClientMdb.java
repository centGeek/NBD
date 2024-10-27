package shop.orm.repository;

import org.bson.codecs.pojo.annotations.BsonCreator;
import org.bson.codecs.pojo.annotations.BsonProperty;
import org.bson.types.ObjectId;
import shop.orm.model.Client;

public class ClientMdb extends AbstractEntityMdb {
    @BsonCreator
    public ClientMdb(@BsonProperty("_id") long _id){


        super(_id);
    }

}
