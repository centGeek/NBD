package shop.orm;

import shop.orm.model.*;

import java.time.LocalDate;

public class TestData {
    public static Client getClient1(){
        Address address = new Address("Lodz", "Poland", "06-323", "Lodzka", "32");
        ClientType clientType = new CompanyClient("03232132911", 323124124324234L, "Politechnika Lodzka");
        return new Client(address, clientType);
    }
    public static Client getClient2(){
        Address address = new Address("Lodz", "Poland", "96-323", "Aleje Politechniki", "7");
        return  new Client(address, new IndividualClient("03222222222","email@gmail.com", LocalDate.of(2022, 10, 21)));
    }
}
