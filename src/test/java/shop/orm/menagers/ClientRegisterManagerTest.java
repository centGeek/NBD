package shop.orm.menagers;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import shop.orm.model.Address;
import shop.orm.model.Client;
import shop.orm.repository.AbstractCassandraRepository;

import java.util.ArrayList;


public class ClientRegisterManagerTest {

    @BeforeAll
    public static void setUp() {
        AbstractCassandraRepository.getDatabase();
    }

    @Test
    public void addingTestCorrectly() {
        try (ClientRegisterManager clientRegisterManager = new ClientRegisterManager()) {
            Client client = TestData.getClient1();
            clientRegisterManager.clientRegister(client);
            Assertions.assertEquals(1, clientRegisterManager.count());

            clientRegisterManager.clientRegister(client);

            //Assertions.assertThrows(RuntimeException.class, () -> clientRegisterManager.clientRegister(client));


            Client client2 = TestData.getClient3();
            clientRegisterManager.clientRegister(client2);
            //System.out.println(clientRegisterManager.getAllClients());

            Assertions.assertEquals(2, clientRegisterManager.count());




            Client client1FromDatabase = clientRegisterManager.getClientById(client.getId());
            Assertions.assertEquals(client, client1FromDatabase);



            clientRegisterManager.clientDelete(client);
            clientRegisterManager.clientDelete(client2);
        } catch (Exception e) {
            Assertions.fail(e.getMessage());
        }
    }

    @Test
    public void clientDeletingSuccessFully() {
        try (ClientRegisterManager clientRegisterManager = new ClientRegisterManager()) {
            Assertions.assertEquals(0, clientRegisterManager.count());

            Client client1 = TestData.getClient1();
            Client client2 = TestData.getClient2();

            clientRegisterManager.clientRegister(client1);
            clientRegisterManager.clientRegister(client2);

            Assertions.assertEquals(2,
                    clientRegisterManager.count());

            clientRegisterManager.clientDelete(client1);

            Assertions.assertEquals(1,
                    clientRegisterManager.count());

            clientRegisterManager.clientDelete(client2);
            Assertions.assertEquals(0, clientRegisterManager.count());
        } catch (Exception e) {
            Assertions.fail(e.getMessage());
        }
    }

    @Test
    public void clientUpdateAddress() {
        try (ClientRegisterManager clientRegisterManager = new ClientRegisterManager()) {
            Client client = TestData.getClient1();
            clientRegisterManager.clientRegister(client);
            Address address = new Address(
                    "Warszawa", "Ksiestwo Warszawskie",
                    "14-10", "Grunwaldzka", "1");
            client = clientRegisterManager.getAllClients().getFirst();
            clientRegisterManager.clientUpdateAddress(client, address);
            Assertions.assertEquals(address,
                    clientRegisterManager.getClientById(client.getId()).getAddress());
            clientRegisterManager.clientDelete(client);
        } catch (Exception e) {
            Assertions.fail(e.getMessage());
        }
    }

    @AfterAll
    public static void closeVirtualConnection() {
        AbstractCassandraRepository.decrementCounter();
    }
}
