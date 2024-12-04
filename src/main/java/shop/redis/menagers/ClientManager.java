package shop.redis.menagers;


import shop.redis.model.Address;
import shop.redis.model.Client;
import shop.redis.repository.mongoDb.ClientMongoRegisterRepository;

import java.util.List;

public class ClientManager implements AutoCloseable {
    private final ClientMongoRegisterRepository clientRegisterRepository;

    public ClientManager() {
        this.clientRegisterRepository = new ClientMongoRegisterRepository();
    }

    public ClientManager(String nameOfCollection) {
        this.clientRegisterRepository = new ClientMongoRegisterRepository(nameOfCollection);
    }

    public void clientRegister(Client client) {
        clientRegisterRepository.clientRegister(client);
    }
    public void getClientByPesel(String pesel) {
        clientRegisterRepository.getClientByPesel(pesel);
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