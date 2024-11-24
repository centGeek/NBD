package shop.redis.menagers;


import shop.redis.model.Address;
import shop.redis.model.Client;
import shop.redis.repository.mongoDb.ClientMongoRegisterRepository;

import java.util.List;

public class ClientRegisterManager implements AutoCloseable {
    private final ClientMongoRegisterRepository clientRegisterRepository;

    public ClientRegisterManager() {
        this.clientRegisterRepository = new ClientMongoRegisterRepository();
    }

    public ClientRegisterManager(String nameOfCollection) {
        this.clientRegisterRepository = new ClientMongoRegisterRepository(nameOfCollection);
    }

    public void clientRegister(Client client) {
        clientRegisterRepository.clientRegister(client);
    }

    public void clientDelete(Client client) {
        clientRegisterRepository.clientDelete(client);
    }

    public List<Client> getAllClients() {
        return clientRegisterRepository.getAllClients();
    }

    public void clientUpdateAddress(Client client, Address address) {
        clientRegisterRepository.clientUpdateAddress(client, address);
    }

    @Override
    public void close() throws Exception {
        clientRegisterRepository.close();
    }
}