package shop.orm.repository.classes;

import com.datastax.oss.driver.api.mapper.annotations.*;
import com.datastax.oss.driver.api.mapper.entity.naming.GetterStyle;
import shop.orm.model.Client;
import shop.orm.model.Product;
import shop.orm.model.Purchase;
import shop.orm.repository.ClientRegisterRepository;
import shop.orm.repository.StockRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Entity(defaultKeyspace = CassandraConsts.DEFAULT_NAMESPACE)
@CqlName(CassandraConsts.PURCHASES_BY_CLIENT_TABLE_NAME)
@PropertyStrategy(mutable = false, getterStyle = GetterStyle.JAVABEANS)
public class PurchaseByClientCassandra {

    @CqlName(CassandraConsts.CLIENT_ID_NAME)
    @PartitionKey
    private UUID clientUUID;

    @ClusteringColumn
    @CqlName(CassandraConsts.PURCHASE_BY_CLIENT_ID_NAME)
    private UUID id;

    @CqlName(CassandraConsts.PURCHASE_BY_CLIENT_PRODUCTS_NAME)
    private List<Map<String, UUID>> productsUUID = new ArrayList<>();


    public PurchaseByClientCassandra(UUID clientUUID, UUID id, List<Map<String, UUID>> productsUUID) {
        this.clientUUID = clientUUID;
        this.id = id;
        this.productsUUID = productsUUID;
    }

    public PurchaseByClientCassandra(Purchase purchase) {
        this.id = purchase.getId();
        this.clientUUID = purchase.getClient().getId();
        ArrayList<Map<String, UUID>> productsUUID = new ArrayList<>();
        for (Product product : purchase.getProducts()) {
            productsUUID.add(Map.of(product.getProductName(), product.getId()));
        }
        this.productsUUID = productsUUID;


    }


    public UUID getClientUUID() {
        return clientUUID;
    }

    public UUID getId() {
        return id;
    }

    public List<Map<String, UUID>> getpurchase_by_client_products() {
        return productsUUID;
    }


    public static Purchase purchaseCassandraToModel(PurchaseByClientCassandra purchaseByClientCassandra) {
        try (ClientRegisterRepository clientRegisterRepository = new ClientRegisterRepository(false);
             StockRepository stockRepository = new StockRepository(false)) {

            Client client = clientRegisterRepository.findById(purchaseByClientCassandra.getClientUUID());

            List<Map<String, UUID>> productsUUID = purchaseByClientCassandra.getpurchase_by_client_products();

            ArrayList<Product> products = new ArrayList<>();

            for (Map<String, UUID> map : productsUUID) {
                for (Map.Entry<String, UUID> entry : map.entrySet()) {
                    products.add(stockRepository.get(entry.getKey(), entry.getValue()));
                }
            }
            return new Purchase(client, products);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public Purchase toModel() {
        return purchaseCassandraToModel(this);
    }

}
