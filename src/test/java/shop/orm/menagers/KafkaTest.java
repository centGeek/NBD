package shop.orm.menagers;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import shop.orm.kafka.KafkaConsumerService;
import shop.orm.kafka.KafkaProducerService;
import shop.orm.model.Address;
import shop.orm.model.Client;
import shop.orm.model.IndividualClient;
import shop.orm.model.Purchase;

import java.time.LocalDate;
import java.util.List;

public class KafkaTest {

    public static final String topic = "test";
    KafkaProducerService kafkaProducerService = new KafkaProducerService(topic);
    @Test
    @Order(1)
    public void testProducerSendMessage() {
        Client client1 = new Client(new Address("Lodz", "Polska", "93-590", "Politechniki", "10")
                , new IndividualClient("931812182312312123126", "xd@gmail.com", LocalDate.EPOCH));
        Purchase purchase = new Purchase(client1, List.of());
        try {
            KafkaConsumerService.createTopic(topic);
            kafkaProducerService.sendEvent(purchase);
        } catch (InterruptedException e) {
            Assertions.fail(e.getMessage());
        }
    }

}

