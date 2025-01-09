package shop.orm.repository.classes;

import com.datastax.oss.driver.api.core.CqlIdentifier;

public final class CassandraConsts {

    public static final String DEFAULT_NAMESPACE = "shop";
    public static final String CLIENT_TABLE_NAME = "client";
    public static final String ADDRESS_TABLE_NAME = "address";
    public static final String CLIENT_TYPE_TABLE_NAME = "client_type";

    public static final CqlIdentifier DEFAULT_NAMESPACE_CQL = CqlIdentifier.fromCql("shop");
    public static final CqlIdentifier CLIENT_TABLE_NAME_CQL = CqlIdentifier.fromCql("client");
    public static final CqlIdentifier ADDRESS_TABLE_NAME_CQL = CqlIdentifier.fromCql("address");
    public static final CqlIdentifier CLIENT_TYPE_TABLE_NAME_CQL = CqlIdentifier.fromCql("client_type");

    //Data type address
    public static final String ADDRESS_DATA_TYPE = "address";


    //Client section
    public static final CqlIdentifier CLIENT_ID = CqlIdentifier.fromCql("client_id");
    public static final String CLIENT_ID_NAME = "client_id";

    public static final CqlIdentifier ADDRESSES_FIELD = CqlIdentifier.fromCql("address");


    private CassandraConsts() {

    }
}
