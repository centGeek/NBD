package shop.redis.redismanagers;

import shop.redis.model.Address;
import shop.redis.model.Client;
import shop.redis.repository.mongoDb.ClientMongoRegisterRepository;
import shop.redis.repository.mongoEntity.ClientMdb;
import shop.redis.repository.redis.ClientRedisRepository;

import java.util.List;

public class ClientRegisterDecorator implements AutoCloseable {
    private final ClientMongoRegisterRepository clientMongoRegisterRepository;

    private final ClientRedisRepository clientRedisRepository;

    public ClientRegisterDecorator() {
        this.clientRedisRepository = new ClientRedisRepository();
        this.clientMongoRegisterRepository = new ClientMongoRegisterRepository();

    }

    public ClientRegisterDecorator(String nameOfCollection) {
        this.clientRedisRepository = new ClientRedisRepository();
        this.clientMongoRegisterRepository = new ClientMongoRegisterRepository(nameOfCollection);
    }

    public void clientRegister(Client client) {
        clientMongoRegisterRepository.clientRegister(client);
        client.setId(null);
        clientRedisRepository.add(client);
    }

    public void clientDelete(Client client) {
        clientMongoRegisterRepository.clientDelete(client);
        clientRedisRepository.deleteClient(client);
    }

    public List<Client> getAllClients() {
        return clientMongoRegisterRepository.getAllClients();
    }

    public Client getClientByPesel(String pesel) {
        return clientRedisRepository.getClientByPesel(pesel)
                .orElseGet(() ->
                        ClientMdb.ClientMdbToClient(
                                clientMongoRegisterRepository.getClientByPesel(pesel).getFirst()));
    }

    public void clientUpdateAddress(Client client, Address address) {
        clientMongoRegisterRepository.clientUpdateAddress(client, address);
        clientRedisRepository.getClientByPesel(client.getClientType().getPesel())
                .ifPresent(c -> clientRedisRepository.clientUpdateAddress(client, address));
    }

    @Override
    public void close() throws Exception {
        clientMongoRegisterRepository.close();
    }

}
