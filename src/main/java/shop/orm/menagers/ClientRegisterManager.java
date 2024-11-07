package shop.orm.menagers;

import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoDatabase;
import org.w3c.dom.Entity;
import shop.orm.model.Address;
import shop.orm.model.Client;
import shop.orm.repository.ClientRegisterRepository;

import java.util.List;

public class ClientRegisterManager implements AutoCloseable{
    private final ClientRegisterRepository clientRegisterRepository ;
    public ClientRegisterManager() {
        this.clientRegisterRepository = new ClientRegisterRepository();
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
