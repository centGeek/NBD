package shop.orm.menagers;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import shop.orm.model.Address;
import shop.orm.model.Client;
import shop.orm.repository.ClientRegisterRepository;

import java.util.List;

public class ClientRegisterManager {
    private ClientRegisterRepository clientRegisterRepository;

    public void clientRegister(Client client, EntityManager entityManager) {
        clientRegisterRepository.clientRegister(client, entityManager);
    }

    public void clientDelete(Client client, EntityManager entityManager) {
        clientRegisterRepository.clientDelete(client, entityManager);
    }

    public List<Client> getAllClients(EntityManager entityManager) {
        return clientRegisterRepository.getAllClients(entityManager);
    }

    public void clientUpdateAddress(Client client, Address address, EntityManager entityManager) {
        clientRegisterRepository.clientUpdateAddress(client, address, entityManager);
    }
}
