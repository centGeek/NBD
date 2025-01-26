package shop.orm.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;
import shop.orm.model.IndividualClient;
import shop.orm.model.Purchase;

import java.util.Properties;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

public class KafkaProducerService {
    private final String topicName;
    private final Producer<String, String> producer;

    public KafkaProducerService(String topicName) {
        this.topicName = topicName;

        Properties producerProps = new Properties();
        producerProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "kafka1:9192,kafka2:9292,kafka3:9392");
        producerProps.put(ProducerConfig.CLIENT_ID_CONFIG, "local-producer");
        producerProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        producerProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        producerProps.put(ProducerConfig.ACKS_CONFIG, "all");
        producerProps.put(ProducerConfig.ENABLE_IDEMPOTENCE_CONFIG, true);


        this.producer = new KafkaProducer<>(producerProps);
    }

    public void sendEvent(Purchase purchase) {
        try {

            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.registerModule(new JavaTimeModule());
            objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
            objectMapper.registerSubtypes(IndividualClient.class);
            String jsonString = objectMapper.writeValueAsString(purchase);

            ProducerRecord<String, String> record = new ProducerRecord<>(topicName,
                    UUID.randomUUID().toString(),
                    jsonString);

            try {
                producer.send(record, (metadata, exception) -> {
                    if (exception != null) {
                        System.err.println("KAFKA SEND ERROR: " + exception.getMessage());
                        exception.printStackTrace();
                    } else {
                        System.out.println("KAFKA SEND SUCCESS: "
                                + "Topic=" + metadata.topic()
                                + " Partition=" + metadata.partition()
                                + " Offset=" + metadata.offset());
                        System.out.println(jsonString);
                    }
                }).get();
            } catch (InterruptedException | ExecutionException e) {
                System.err.println("KAFKA SEND FAILED COMPLETELY: " + e.getMessage());
                e.printStackTrace();
            }
        } catch (JsonProcessingException e) {
            System.err.println("JSON SERIALIZATION ERROR: " + e.getMessage());
            e.printStackTrace();
        }
    }


    public void close() {
        producer.close();
    }
}