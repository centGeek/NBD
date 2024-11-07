package shop.orm.repository.MongoDBClasses;

import org.bson.codecs.pojo.annotations.BsonDiscriminator;
import org.bson.codecs.pojo.annotations.BsonProperty;

@BsonDiscriminator(key = "_t",value = "Company")
public class CompanyClientMdb implements ClientTypeMdb {
    public CompanyClientMdb(String companyNameMdb, long NIPMdb) {
        this.companyNameMdb = companyNameMdb;
        this.NIPMdb = NIPMdb;
    }

    @BsonProperty
    private String companyNameMdb;
    @BsonProperty
    private long NIPMdb;
}
