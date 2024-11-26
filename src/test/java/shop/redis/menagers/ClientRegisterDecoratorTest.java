package shop.redis.menagers;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import shop.redis.model.Client;
import shop.redis.decorators.ClientRegisterDecorator;
import shop.redis.repository.mongoDb.ClientMongoRegisterRepository;
import shop.redis.repository.redis.CacheService;

public class ClientRegisterDecoratorTest {

    @BeforeEach
    public void deleteFromDatabase(){
        CacheService cacheService = new CacheService();
        cacheService.flushAll();

        var mongoRepository = new ClientMongoRegisterRepository();
        mongoRepository.deleteAll();
    }
    @Test
    public void thatClientRegisteringWentCorrectly(){
        var clientRegisterDecorator = new ClientRegisterDecorator();
        var client = TestData.getClient1();

        clientRegisterDecorator.clientRegister(client);
        var allClients = clientRegisterDecorator.getAllClients();

        Assertions.assertEquals(client, allClients.getFirst());
    }
    @Test
    public void thatClientReadingThrowsException(){
        var clientRegisterDecorator = new ClientRegisterDecorator();
        var client = TestData.getClient1();

        clientRegisterDecorator.clientRegister(client);
        clientRegisterDecorator.clientDelete(client);

        Assertions.assertThrows(RuntimeException.class, () -> clientRegisterDecorator
                .getClientByPesel(client.getClientType().getPesel()));
    }
    @Test
    public void thatClientDeletingAndReadingWentCorrectly(){
        var clientRegisterDecorator = new ClientRegisterDecorator();
        var client1 = TestData.getClient1();
        var client2 = TestData.getClient2();

        clientRegisterDecorator.clientRegister(client1);
        clientRegisterDecorator.clientRegister(client2);
        clientRegisterDecorator.clientDelete(client1);

        Client clientByPesel = clientRegisterDecorator
                .getClientByPesel(client2.getClientType().getPesel());

        Assertions.assertEquals(clientByPesel, client2);
    }
}
