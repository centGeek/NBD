package shop.redis.menagers;

import shop.redis.model.Address;
import shop.redis.model.Client;
import shop.redis.repository.mongoDb.ClientMongoRegisterRepository;
import shop.redis.repository.mongoEntity.ClientMdb;
import shop.redis.repository.redis.ClientRedisRepository;

import java.util.List;

public class ClientRegisterManager implements AutoCloseable {
    private final ClientMongoRegisterRepository clientMongoRegisterRepository;

    private final ClientRedisRepository clientRedisRepository;

    public ClientRegisterManager() {
        this.clientRedisRepository = new ClientRedisRepository();
        this.clientMongoRegisterRepository = new ClientMongoRegisterRepository();

    }

    public ClientRegisterManager(String nameOfCollection) {
        this.clientRedisRepository = new ClientRedisRepository();
        this.clientMongoRegisterRepository = new ClientMongoRegisterRepository(nameOfCollection, clientRedisRepository);
    }

    public void clientRegister(Client client) {
        clientMongoRegisterRepository.clientRegister(client);
//        clientRedisRepository.add(client);
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
