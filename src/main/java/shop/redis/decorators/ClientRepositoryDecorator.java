package shop.redis.decorators;

import shop.redis.model.Address;
import shop.redis.model.Client;
import shop.redis.repository.mongoDb.ClientMongoRegisterRepository;
import shop.redis.repository.mongoEntity.ClientMdb;
import shop.redis.repository.redis.ClientRedisRepository;

import java.util.List;
import java.util.UUID;

public class ClientRepositoryDecorator implements IClientRepository, AutoCloseable {
    private final ClientMongoRegisterRepository clientMongoRegisterRepository;

    private final ClientRedisRepository clientRedisRepository;

    public ClientRepositoryDecorator() {
        this.clientRedisRepository = new ClientRedisRepository();
        this.clientMongoRegisterRepository = new ClientMongoRegisterRepository();

    }

    public ClientRepositoryDecorator(String nameOfCollection) {
        this.clientRedisRepository = new ClientRedisRepository();
        this.clientMongoRegisterRepository = new ClientMongoRegisterRepository(nameOfCollection);
    }

    public void clientRegister(Client client) {
        String id = clientMongoRegisterRepository.clientRegister(client);
        client.setId(UUID.fromString(id));
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
        var client = clientRedisRepository.getClientByPesel(pesel);
        if (client.isPresent()) {
            return client.get();
        }
        var clientMdb = clientMongoRegisterRepository.getClientByPesel(pesel);
        if (clientMdb != null) {
            return ClientMdb.ClientMdbToClient(clientMdb);
        }
        return null;
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

    public boolean isClientRegistered(Client client) {
        Client clientByPesel = getClientByPesel(client.getClientType().getPesel());
        return clientByPesel != null;
    }
}
