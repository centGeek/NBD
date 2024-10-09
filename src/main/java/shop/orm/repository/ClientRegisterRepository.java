package shop.orm.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import shop.orm.model.Address;
import shop.orm.model.Client;

import java.util.List;

public class ClientRegisterRepository {

    public void clientRegister(Client client, EntityManager entityManager){
        entityManager.getTransaction().begin();
        entityManager.persist(client);
        entityManager.getTransaction().commit();
    }
    public void clientDelete(Client client, EntityManager entityManager){
        entityManager.getTransaction().begin();
        entityManager.remove(client);
        entityManager.getTransaction().commit();
    }
    public void clientUpdateAddress(Client client, Address address, EntityManager entityManager) {
        entityManager.getTransaction().begin();
        client.setAddress(address);
        entityManager.getTransaction().commit();
    }
    public List<Client> getAllClients(EntityManager entityManager){
        entityManager.getTransaction().begin();
        String selectQuery = "SELECT c FROM Client c";
        Query query = entityManager.createQuery(selectQuery);
        List<Client> clients = query.getResultList();
        entityManager.getTransaction().commit();

        return clients;
    }
}
