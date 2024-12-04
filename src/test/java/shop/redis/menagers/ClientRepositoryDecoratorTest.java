package shop.redis.menagers;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import shop.redis.decorators.ClientRepositoryDecorator;
import shop.redis.model.Client;
import shop.redis.repository.mongoDb.ClientMongoRegisterRepository;
import shop.redis.repository.redis.CacheService;

public class ClientRepositoryDecoratorTest {

    private final ClientRepositoryDecorator clientRegisterDecoratorImpl = new ClientRepositoryDecorator();

    @BeforeEach
    public void deleteFromDatabase() {
        var cacheService = new CacheService();
        cacheService.flushAll();

        var mongoRepository = new ClientMongoRegisterRepository();
        mongoRepository.deleteAll();
    }

    @Test
    public void thatClientRegisteringWentCorrectly() {
        var client = TestData.getClient1();

        clientRegisterDecoratorImpl.clientRegister(client);
        var allClients = clientRegisterDecoratorImpl.getAllClients();

        Assertions.assertEquals(client, allClients.getFirst());
    }

    @Test
    public void thatClientReadingThrowsException() {
        var client = TestData.getClient1();

        clientRegisterDecoratorImpl.clientRegister(client);
        clientRegisterDecoratorImpl.clientDelete(client);

        Assertions.assertThrows(RuntimeException.class, () -> clientRegisterDecoratorImpl
                .getClientByPesel(client.getClientType().getPesel()));
    }

    @Test
    public void thatClientDeletingAndReadingWentCorrectly() {

        var client1 = TestData.getClient1();
        var client2 = TestData.getClient2();

        clientRegisterDecoratorImpl.clientRegister(client1);
        clientRegisterDecoratorImpl.clientRegister(client2);
        clientRegisterDecoratorImpl.clientDelete(client1);

        Client clientByPesel = clientRegisterDecoratorImpl
                .getClientByPesel(client2.getClientType().getPesel());
        Assertions.assertEquals(clientByPesel, client2);
    }

    @Test
    public void thatClientAddressUpdateWentCorrectly() {
        var client2 = TestData.getClient2();
        clientRegisterDecoratorImpl.clientRegister(client2);
        clientRegisterDecoratorImpl.clientUpdateAddress(client2, TestData.getClient3().getAddress());

        var clientByPesel = clientRegisterDecoratorImpl
                .getClientByPesel(client2.getClientType().getPesel());

        client2.setAddress(TestData.getClient3().getAddress());

        Assertions.assertEquals(client2, clientByPesel);
    }
}
