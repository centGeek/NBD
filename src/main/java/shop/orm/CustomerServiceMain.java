package shop.orm;


import shop.orm.kafka.KafkaConsumerService;


public class CustomerServiceMain {

    public static void main(String[] args) throws InterruptedException {

        KafkaConsumerService kafkaConsumerService = new KafkaConsumerService();
        KafkaConsumerService.createTopic("purchase-topic-created");
        kafkaConsumerService.consumeTopicsByGroup("purchase-topic-created");
    }

}
