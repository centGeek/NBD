package shop.orm;

import shop.orm.kafka.KafkaConsumerService;
import shop.orm.kafka.KafkaProducerService;
import shop.orm.model.Client;
import shop.orm.model.Purchase;

import java.util.List;

public class ProducerServiceMain {
    public static void main(String[] args) {
        KafkaProducerService kafkaProducerService = new KafkaProducerService("hello");
        kafkaProducerService.sendEvent(new Purchase(new Client(null, null, null), List.of()));
    }
}
