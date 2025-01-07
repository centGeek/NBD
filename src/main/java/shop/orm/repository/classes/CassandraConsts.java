package shop.orm.repository.classes;

import com.datastax.oss.driver.api.core.CqlIdentifier;

public final class CassandraConsts {

    public static final String DEFAULT_NAMESPACE = "shop";
    public static final String CLIENT_TABLE_NAME = "client";
    public static final String ADDRESS_TABLE_NAME = "address";

    public static final CqlIdentifier DEFAULT_NAMESPACE_CQL = CqlIdentifier.fromCql("shop");
    public static final CqlIdentifier CLIENT_TABLE_NAME_CQL = CqlIdentifier.fromCql("client");


    private CassandraConsts() {

    }
}
