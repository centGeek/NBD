package shop.orm;


import shop.orm.kafka.KafkaConsumerService;


public class CustomerServiceMain {

    public static void main(String[] args) throws InterruptedException {

        KafkaConsumerService kafkaConsumerService = new KafkaConsumerService();
        kafkaConsumerService.consumeTopicsByGroup("hello");
    }

}
