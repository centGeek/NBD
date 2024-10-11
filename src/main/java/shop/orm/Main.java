package shop.orm;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import shop.orm.menagers.ClientRegisterManager;
import shop.orm.model.Address;
import shop.orm.model.Client;
import shop.orm.model.IndividualClient;

import java.time.LocalDate;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("NBD-unit");
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        LocalDate localDate = LocalDate.of(2022, 3, 12);
        Address address = new Address(1, "Lodz", "Poland", "96-323", "Aleje Politechniki", "7");
        Client client = new Client(1L,  address, new IndividualClient(localDate));
        ClientRegisterManager clientRegisterManager = new ClientRegisterManager();
        clientRegisterManager.clientRegister(client, entityManager);
        List<Client> allClients = clientRegisterManager.getAllClients(entityManager);
        System.out.println(allClients);
    }
}
