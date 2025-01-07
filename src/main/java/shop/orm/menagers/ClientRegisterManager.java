package shop.orm.menagers;


import shop.orm.model.Address;
import shop.orm.model.Client;
import shop.orm.repository.ClientRegisterRepository;

import java.util.List;

public class ClientRegisterManager implements AutoCloseable {
    private final ClientRegisterRepository clientRegisterRepository;

    public ClientRegisterManager() {
        this.clientRegisterRepository = new ClientRegisterRepository();
    }

    public void clientRegister(Client client) {
        clientRegisterRepository.clientRegister(client);
    }

    public void clientDelete(Client client) {
        clientRegisterRepository.clientDelete(client);
    }

//    public List<Client> getAllClients() {
//        return clientRegisterRepository.getAllClients();
//    }

    public void clientUpdateAddress(Client client, Address address) {
        clientRegisterRepository.clientUpdateAddress(client, address);
    }

    public long count(){
        return clientRegisterRepository.count();
    }

    @Override
    public void close() throws Exception {
        clientRegisterRepository.close();
    }
}
