package shop.redis.repository.mongoEntity;

import org.bson.BsonReader;
import org.bson.BsonWriter;
import org.bson.codecs.Codec;
import org.bson.codecs.DecoderContext;
import org.bson.codecs.EncoderContext;

public class ClientTypeMdbCodec implements Codec<ClientTypeMdb> {

    @Override
    public void encode(BsonWriter writer, ClientTypeMdb clientTypeMdb, EncoderContext encoderContext) {
        writer.writeStartDocument();

        // Zapisz discriminator "_clazz" na podstawie typu obiektu
        if (clientTypeMdb instanceof CompanyClientMdb company) {
            writer.writeString("_id", company.getEntityId());
            writer.writeString("pesel", company.getPesel());
            writer.writeString("_clazz", "Company");
            writer.writeString("companyName", company.getCompanyNameMdb());
            writer.writeString("nip", company.getNIPMdb());

        } else if (clientTypeMdb instanceof IndividualClientMdb individual) {
            writer.writeString("_id", individual.getEntityId());
            writer.writeString("pesel", individual.getPesel());
            writer.writeString("_clazz", "Individual");
            writer.writeString("email", individual.getEmail());
            writer.writeString("birthData", individual.getBirthData());
        }

        writer.writeEndDocument();
    }

    @Override
    public Class<ClientTypeMdb> getEncoderClass() {
        return ClientTypeMdb.class;
    }

    @Override
    public ClientTypeMdb decode(BsonReader reader, DecoderContext decoderContext) {
        reader.readStartDocument();
        String id = reader.readString("_id");
        String pesel = reader.readString("pesel");
        String clazz = reader.readString("_clazz");
        ClientTypeMdb clientType = null;

        // Wybierz odpowiednią podklasę na podstawie wartości `_clazz`
        if ("Company".equals(clazz)) {
            String companyName = reader.readString("companyName");
            String nip = reader.readString("nip");
            clientType = new CompanyClientMdb(id, pesel, companyName, nip);
        } else if ("Individual".equals(clazz)) {
            String email = reader.readString("email");
            String birthData = reader.readString("birthData");
            clientType = new IndividualClientMdb(id, pesel, email, birthData);
        }

        reader.readEndDocument();
        return clientType;
    }
}
