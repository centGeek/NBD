package shop.orm.menagers;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import shop.orm.TestData;
import shop.orm.model.Client;

public class ClientRegisterManagerTest {

    @Test
    public void addingTestCorrectly() {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("NBD-unit");
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        ClientRegisterManager clientRegisterManager = new ClientRegisterManager();

        Assertions.assertEquals(0, clientRegisterManager.getAllClients(entityManager).size());

        Client client = TestData.getClient1();
        clientRegisterManager.clientRegister(client, entityManager);
        Assertions.assertEquals(1, clientRegisterManager.getAllClients(entityManager).size());

        client = TestData.getClient1();
        clientRegisterManager.clientRegister(client, entityManager);
        Assertions.assertEquals(1, clientRegisterManager.getAllClients(entityManager).size());

        client = TestData.getClient2();
        clientRegisterManager.clientRegister(client, entityManager);
        Assertions.assertEquals(2, clientRegisterManager.getAllClients(entityManager).size());


    }
}
