package shop.orm.repository.MongoDBClasses;

import lombok.Getter;
import org.bson.codecs.pojo.annotations.BsonCreator;
import org.bson.codecs.pojo.annotations.BsonDiscriminator;
import org.bson.codecs.pojo.annotations.BsonProperty;
import shop.orm.model.Client;
import shop.orm.model.Product;
import shop.orm.model.Purchase;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class PurchaseMdb extends AbstractEntityMdb {

    @BsonCreator
    public PurchaseMdb(Purchase purchase) {
        super(purchase.getId().toString());
        this.client = new ClientMdb(purchase.getClient());
        //this.products = purchase.getProducts();
    }

    @BsonProperty("client")
    private ClientMdb client;
    @BsonProperty("products")
    private List<ProductMdb> products = new ArrayList<>();

}


