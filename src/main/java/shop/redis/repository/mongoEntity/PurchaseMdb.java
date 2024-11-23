package shop.redis.repository.mongoEntity;

import org.bson.codecs.pojo.annotations.BsonCreator;

import org.bson.codecs.pojo.annotations.BsonProperty;
import shop.redis.model.Product;
import shop.redis.model.Purchase;

import java.util.ArrayList;
import java.util.List;

public class PurchaseMdb extends AbstractEntityMdb {


    public PurchaseMdb(Purchase purchase) {
        super(purchase.getId().toString());
        this.client = new ClientMdb(purchase.getClient());
        List<Product> products = purchase.getProducts();
        for (Product product : products) {
            this.products.add(new ProductMdb(product));
        }
    }

    @BsonCreator
    public PurchaseMdb(
            @BsonProperty("_id") String id,
            @BsonProperty("client") ClientMdb clientMdb,
            @BsonProperty("products") List<ProductMdb> products
    ) {
        super(id);
        this.client = clientMdb;
        this.products = products != null ? products : new ArrayList<>();
    }

    @BsonProperty("client")
    private ClientMdb client;
    @BsonProperty("products")
    private List<ProductMdb> products = new ArrayList<>();

    public ClientMdb getClient() {
        return client;
    }

    public List<ProductMdb> getProducts() {
        return products;
    }

    public void buyProducts() {
        for (ProductMdb product : products) {
            product.buyProduct();
        }
    }


    public static Purchase purchaseMdbToPurchase(PurchaseMdb purchaseMdb) {
        List<ProductMdb> productMdbs = purchaseMdb.getProducts();
        List<Product> productList = new ArrayList<>();
        for (ProductMdb product : productMdbs) {
            productList.add(ProductMdb.productFromProductMdb(product));
        }
        return new Purchase(
                ClientMdb.ClientMdbToClient(purchaseMdb.getClient()),
                productList
        );
    }
}


