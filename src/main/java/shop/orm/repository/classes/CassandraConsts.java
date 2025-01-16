package shop.orm.repository.classes;

import com.datastax.oss.driver.api.core.CqlIdentifier;

public final class CassandraConsts {

    public static final String DEFAULT_NAMESPACE = "shop";
    public static final String CLIENT_TABLE_NAME = "client";
    public static final String ADDRESS_TABLE_NAME = "address";
    public static final String CLIENT_TYPE_TABLE_NAME = "client_type";
    public static final String PRODUCT_TABLE_NAME = "product";


    public static final CqlIdentifier DEFAULT_NAMESPACE_CQL = CqlIdentifier.fromCql("shop");
    public static final CqlIdentifier CLIENT_TABLE_NAME_CQL = CqlIdentifier.fromCql("client");
    public static final CqlIdentifier ADDRESS_TABLE_NAME_CQL = CqlIdentifier.fromCql("address");
    public static final CqlIdentifier CLIENT_TYPE_TABLE_NAME_CQL = CqlIdentifier.fromCql("client_type");
    public static final CqlIdentifier PRODUCT_TABLE_NAME_CQL = CqlIdentifier.fromCql("product");

    //Data type address
    public static final String ADDRESS_DATA_TYPE = "address";


    //Client section
    public static final CqlIdentifier CLIENT_ID = CqlIdentifier.fromCql("client_id");
    public static final String CLIENT_ID_NAME = "client_id";

    public static final CqlIdentifier ADDRESSES_FIELD = CqlIdentifier.fromCql("address");

    //Product section
    public static final String PRODUCT_ID_NAME = "product_id";
    public static final CqlIdentifier PRODUCT_ID_CQL = CqlIdentifier.fromCql("product_id");

    public static final CqlIdentifier PRODUCT_NAME_CQL = CqlIdentifier.fromCql("product_name");
    public static final String PRODUCT_NAME = "product_name";

    public static final CqlIdentifier PRODUCT_PRICE_CQL = CqlIdentifier.fromCql("product_price");
    public static final String PRODUCT_PRICE_NAME = "product_price";

    public static final CqlIdentifier PRODUCT_BOUGHT_CQL = CqlIdentifier.fromCql("product_bought");
    public static final String PRODUCT_BOUGHT_NAME = "product_bought";

    private CassandraConsts() {

    }
}
