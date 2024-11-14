package shop.orm.menagers;

import org.bson.codecs.pojo.annotations.BsonCreator;
import org.bson.codecs.pojo.annotations.BsonProperty;
import shop.orm.repository.MongoDBClasses.AbstractEntityMdb;

import java.util.UUID;

public class TestEntity extends AbstractEntityMdb {
    @BsonCreator
    public TestEntity(@BsonProperty("_id") String _id, @BsonProperty("data") String data ){
        super(_id);
        this.data = data;
    }
    @BsonProperty("data")
    private String data;

    public String getData() {
        return data;
    }

    public TestEntity(String data) {
        super(UUID.randomUUID().toString());
        this.data = data;
    }

}
