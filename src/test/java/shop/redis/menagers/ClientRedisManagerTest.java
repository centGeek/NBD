package shop.redis.menagers;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import shop.redis.repository.mongoDb.ClientMongoRegisterRepository;

public class ClientRedisManagerTest {


    @Test
    public void thatClientRegisteringWentCorrectly(){
        var clientMongoRegisterRepository = new ClientMongoRegisterRepository();
        clientMongoRegisterRepository.deleteAll();

        var clientRegisterManager = new ClientRegisterManager();
        var client = TestData.getClient1();

        clientRegisterManager.clientRegister(client);
        var allClients = clientRegisterManager.getAllClients();

        Assertions.assertEquals(client, allClients.getFirst());

    }
}
