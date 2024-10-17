package shop.orm.menagers;

import jakarta.persistence.EntityManager;
import org.checkerframework.checker.units.qual.A;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import shop.orm.TestData;
import shop.orm.model.Address;
import shop.orm.model.Client;

public class ClientRegisterManagerTest {

    @Test
    @org.junit.jupiter.api.Order(2)
    public void addingTestCorrectly() {
        EntityManager entityManager = EntityManagerClassSingleton.getEntityManager();
        ClientRegisterManager clientRegisterManager = new ClientRegisterManager();
        clientRegisterManager.clientDelete(TestData.getClient2(), entityManager);
        Client client = TestData.getClient1();
        clientRegisterManager.clientRegister(client, entityManager);
        Assertions.assertEquals(1, clientRegisterManager.getAllClients(entityManager).size());

        clientRegisterManager.clientRegister(client, entityManager);
        Assertions.assertEquals(1, clientRegisterManager.getAllClients(entityManager).size());

        client = TestData.getClient3();
        clientRegisterManager.clientRegister(client, entityManager);
        System.out.println(clientRegisterManager.getAllClients(entityManager));
        Assertions.assertEquals(2, clientRegisterManager.getAllClients(entityManager).size());
    }
    @Test
    @org.junit.jupiter.api.Order(1)
    public void clientDeletingSuccessFully() {
        EntityManager entityManager = EntityManagerClassSingleton.getEntityManager();
        ClientRegisterManager clientRegisterManager = new ClientRegisterManager();

        Assertions.assertEquals(0, clientRegisterManager.getAllClients(entityManager).size());

        Client client1 = TestData.getClient1();
        Client client2 = TestData.getClient2();

        clientRegisterManager.clientRegister(client1, entityManager);
        clientRegisterManager.clientRegister(client2, entityManager);
        Assertions.assertEquals(2,
                clientRegisterManager.getAllClients(entityManager).size());
        clientRegisterManager.clientDelete(
                clientRegisterManager.getAllClients(entityManager).get(0),
                entityManager);
        Assertions.assertEquals(1,
                clientRegisterManager.getAllClients(entityManager).size());

    }
    @Test
    @org.junit.jupiter.api.Order(3)
    public void clientUpdateAddress() {

        EntityManager entityManager = EntityManagerClassSingleton.getEntityManager();
        ClientRegisterManager clientRegisterManager = new ClientRegisterManager();


        Client client = TestData.getClient1();

        clientRegisterManager.clientRegister(client, entityManager);
        Address address = new Address(
                "Warszawa", "Ksiestwo Warszawskie",
                "14-10", "Grunwaldzka", "1");
        client = clientRegisterManager.getAllClients(entityManager).get(0);
        clientRegisterManager.clientUpdateAddress(client, address,entityManager);
        Assertions.assertEquals(address,
                clientRegisterManager.getAllClients(entityManager).get(0).getAddress());

    }
    @AfterAll
    public static void closeAll(){
        EntityManagerClassSingleton.closeEntityManagerFactory();
        EntityManagerClassSingleton.closeEntityManager();
    }
}
