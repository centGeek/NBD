package shop.redis.decorators;

import shop.redis.model.Address;
import shop.redis.model.Client;

import java.util.List;
/* służy jako odciązenie dla mongo (który służy jako fallback)
dane klienta są często aktualizowane, poprzez sprawdzanie statusu zamówień (potencjalnie logowanie, czy status konta)
 */
public interface IClientRepository {
    void clientRegister(Client client);
    void clientDelete(Client client);
    List<Client> getAllClients();
    Client getClientByPesel(String pesel);

    void clientUpdateAddress(Client client, Address address);
}
