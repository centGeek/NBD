package shop.orm.repository.classes;

import com.datastax.oss.driver.api.mapper.annotations.PartitionKey;

import java.io.Serializable;
import java.util.UUID;

public abstract class AbstractEntity implements Serializable {

    @PartitionKey
    private UUID entityId;

    public UUID getEntityId() {
        return entityId;
    }
    public AbstractEntity(UUID entityId) {
        this.entityId = entityId;
    }
}
