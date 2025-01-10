package shop.orm.repository.classes;

import com.datastax.oss.driver.api.core.CqlIdentifier;

public class ClientTypeConsts {
    public static final CqlIdentifier PESEL_CQL = CqlIdentifier.fromCql("pesel");
    public static final CqlIdentifier DISCRIMINATOR = CqlIdentifier.fromCql("discriminator");
    public static final CqlIdentifier NIP = CqlIdentifier.fromCql("nip");
    public static final CqlIdentifier COMPANY_NAME = CqlIdentifier.fromCql("company_name");
    public static final CqlIdentifier EMAIL = CqlIdentifier.fromCql("email");
    public static final CqlIdentifier BIRTHDATE = CqlIdentifier.fromCql("birth_date");


    public static final String DISCRIMINATOR_STRING = "discriminator";

    public static final String PESEL_CQL_STRING = "pesel";
    public static final String EMAIL_STRING = "email";
    public static final String COMPANY_NAME_STRING = "company_name";
    public static final String BIRTHDATE_STRING = "birth_date";



    private ClientTypeConsts() {}
}
