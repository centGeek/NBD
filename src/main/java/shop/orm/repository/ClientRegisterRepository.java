package shop.orm.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import shop.orm.model.Address;
import shop.orm.model.Client;
import shop.orm.model.ClientType;

import java.util.List;

public class ClientRegisterRepository {
    protected static final Logger logger = LogManager.getLogger(ClientRegisterRepository.class);

    public void clientRegister(Client client, EntityManager entityManager) {
        try {
            String pesel = client.getClientType().getPesel();
            List<ClientType> clientByPesel = this.getClientByPesel(entityManager, pesel);
            if (clientByPesel.isEmpty()) {
                entityManager.getTransaction().begin();
                entityManager.persist(client.getAddress());
                entityManager.persist(client.getClientType());
                entityManager.persist(client);
                entityManager.getTransaction().commit();
            } else {
                logger.log(Level.ERROR, String.format("Can not register client. Client with pesel: %s already exists", pesel));
            }
        }catch (Exception e){
            logger.log(Level.ERROR, "Registering client did not went correctly");
        }
    }

    public void clientDelete(Client client, EntityManager entityManager) {
        try {
            entityManager.getTransaction().begin();
            entityManager.remove(client);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            if (entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }
            logger.log(Level.ERROR, e);
        }
    }

    public void clientUpdateAddress(Client client, Address address, EntityManager entityManager) {
        try {
            entityManager.getTransaction().begin();

            if (address.getAddressId() == null) {
                entityManager.persist(address);
            }
            client.setAddress(address);
            entityManager.merge(client);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            if (entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }
            logger.log(Level.ERROR, e);
        }
    }

    public List<Client> getAllClients(EntityManager entityManager) {
        String selectQuery = "SELECT c FROM Client c";
        entityManager.getTransaction().begin();
        Query query = entityManager.createQuery(selectQuery);
        List<Client> clients = query.getResultList();
        entityManager.getTransaction().commit();
        return clients;
    }

    public List<ClientType> getClientByPesel(EntityManager entityManager, String pesel) {
        String selectQuery = "SELECT ct FROM ClientType ct where ct.pesel =:pesel";
        TypedQuery<ClientType> query = entityManager.createQuery(selectQuery, ClientType.class);
        query.setParameter("pesel", pesel);
        return query.getResultList();
    }
}
