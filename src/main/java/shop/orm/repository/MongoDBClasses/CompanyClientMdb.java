package shop.orm.repository.MongoDBClasses;

import org.bson.codecs.pojo.annotations.BsonCreator;
import org.bson.codecs.pojo.annotations.BsonDiscriminator;
import org.bson.codecs.pojo.annotations.BsonProperty;

import java.util.Objects;

@BsonDiscriminator(key = "_clazz",value = "Company")
public class CompanyClientMdb extends ClientTypeMdb {
    @BsonCreator
    public CompanyClientMdb(
            @BsonProperty("_id") String _id,
            @BsonProperty("pesel") String pesel,
            @BsonProperty("companyName") String companyNameMdb,
            @BsonProperty("nip") String NIPMdb) {
        super(_id,pesel);
        this.companyNameMdb = companyNameMdb;
        this.NIPMdb = NIPMdb;
    }

    @BsonProperty("companyName")
    private String companyNameMdb;
    @BsonProperty("nip")
    private String NIPMdb;


    public String getCompanyNameMdb() {
        return companyNameMdb;
    }

    public String getNIPMdb() {
        return NIPMdb;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CompanyClientMdb that = (CompanyClientMdb) o;
        return Objects.equals(getCompanyNameMdb(), that.getCompanyNameMdb()) && Objects.equals(getNIPMdb(), that.getNIPMdb());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getCompanyNameMdb(), getNIPMdb());
    }
}
