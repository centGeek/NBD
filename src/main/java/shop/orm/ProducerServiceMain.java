package shop.orm;

import shop.orm.kafka.KafkaProducerService;
import shop.orm.model.*;

import java.time.LocalDate;
import java.util.List;

public class ProducerServiceMain {


    public static void main(String[] args) {
        KafkaProducerService kafkaProducerService = new KafkaProducerService("purchase-topic-created");
        Client client1 = new Client(new Address("Lodz", "Polska", "93-590", "Politechniki", "10")
                ,new IndividualClient("931812182312312123126", "xd@gmail.com", LocalDate.EPOCH));
        Purchase purchase = new Purchase(client1, List.of());
        kafkaProducerService.sendEvent(purchase);
    }
}
