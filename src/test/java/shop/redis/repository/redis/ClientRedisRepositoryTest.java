package shop.redis.repository.redis;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import shop.redis.menagers.TestData;
import shop.redis.model.Address;
import shop.redis.model.Client;

import java.util.Optional;

class ClientRedisRepositoryTest {
    @Test
    public void thatClientRegisterAndGetWentCorrectly(){
        var ClientRegisterRepository = new ClientRedisRepository();
        var expectedClient = TestData.getClient2();

        ClientRegisterRepository.add(expectedClient);
        var actualClientByPesel = ClientRegisterRepository.getClientByPesel(expectedClient.getClientType().getPesel());

        Assertions.assertEquals(expectedClient, actualClientByPesel.get());
    }
    @Test
    public void thatClientDeletingWorkCorrectly(){
        var clientRegisterRepository = new ClientRedisRepository();

        Client client = TestData.getClient1();
        clientRegisterRepository.add(client);
        Client anotherClient = TestData.getClient2();
        clientRegisterRepository.add(anotherClient);

        clientRegisterRepository.deleteClient(client);

        Assertions.assertEquals(clientRegisterRepository.getClientByPesel(client.getClientType().getPesel()), Optional.empty());
        Assertions.assertEquals(clientRegisterRepository.getClientByPesel(anotherClient.getClientType().getPesel()).get(),
                anotherClient);
    }
    @Test
    public void thatClientUpdateWentCorrectly(){
        var clientRegisterRepository = new ClientRedisRepository();
        Client client = TestData.getClient1();
        clientRegisterRepository.add(client);
        clientRegisterRepository.clientUpdateAddress(client, TestData.getClient2().getAddress());

        Optional<Client> clientByPesel = clientRegisterRepository.getClientByPesel(client.getClientType().getPesel());

        Assertions.assertEquals(clientByPesel.get().getAddress(), TestData.getClient2().getAddress());

    }

}