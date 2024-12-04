package shop.redis.benchmark;

import shop.redis.model.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class PerformanceFixtures {
    public static Client getClient1() {
        Address address = new Address("Lodz", "Poland", "06-323", "Lodzka", "32");
        ClientType clientType = new CompanyClient("0323212321323213132911", 323124124324234L, "Politechnika Lodzka");
        return new Client(address, clientType);
    }

    public static Client getClient2() {
        Address address = new Address("Lodz", "Poland", "96-323", "Aleje Politechniki", "7");
        return new Client(address, new IndividualClient("03222892222", "email@gmail.com", LocalDate.of(2022, 10, 21)));
    }

    public static Client getClient3() {
        Address address = new Address("Zgierz", "Poland", "12-001", "Zbierzowa", "7");
        return new Client(address, new IndividualClient("03224222111", "email2@gmail.com", LocalDate.of(2022, 10, 21)));
    }
    public static Client getClient4() {
        Address address = new Address("Zgierz", "Poland", "12-001", "Zbierzowa", "7");
        return new Client(address, new IndividualClient("03224546534154222111", "email2@gmail.com", LocalDate.of(2022, 10, 21)));
    }
    public static Client getClient5() {
        Address address = new Address("Zgierz", "Poland", "12-001", "Zbierzowa", "7");
        return new Client(address, new IndividualClient("032242123123412422111", "email2@gmail.com", LocalDate.of(2022, 10, 21)));
    }

    public static Product getProduct1() {
        return new Product("Knoppers", BigDecimal.valueOf(3));
    }

    public static Product getProduct2() {
        return new Product("Grzeski", BigDecimal.valueOf(4));
    }

    public static Purchase getPurchase1() {
        return new Purchase(getClient2(), List.of(getProduct1(), getProduct2()));
    }
    public static Purchase getPurchase2() {
        return new Purchase(getClient3(), List.of(getProduct1(), getProduct2()));
    }public static Purchase getPurchase3() {
        return new Purchase(getClient3(), List.of(getProduct1(), getProduct1()));
    }

}
