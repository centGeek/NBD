package shop.orm.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.errors.WakeupException;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.UUIDDeserializer;
import shop.orm.menagers.PurchaseManager;
import shop.orm.model.Purchase;

import java.text.MessageFormat;
import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import java.util.UUID;

public class KafkaConsumerService {

    private static final PurchaseManager purchaseManager = new PurchaseManager();
    private static String topics;

    public void initConsumerGroup() {
        Properties consumerConfig = new Properties();
        consumerConfig.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, UUIDDeserializer.class.getName());
        consumerConfig.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        consumerConfig.put(ConsumerConfig.GROUP_ID_CONFIG, "grupa");
        consumerConfig.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "kafka1:9192,kafka2:9292,kafka3:9392");
        consumerConfig.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "false");


        KafkaConsumer<UUID, String> consumer = new KafkaConsumer<>(consumerConfig);
        consumer.subscribe(List.of(topics));
        consume(consumer);
    }

    private static void consume(KafkaConsumer<UUID, String> consumer) {
        try {
            consumer.poll(0);
            Set<TopicPartition> consumerAssigment = consumer.assignment();
            System.out.println("Assigned partitions: " + consumerAssigment);
            consumer.seekToBeginning(consumerAssigment);

            Duration timeout = Duration.of(100, ChronoUnit.MILLIS);
            MessageFormat formattter = new MessageFormat("Konsument {5}, Temat {0}, partycja {1}, offset {2, number, integer}, klucz {3}, wartość {4}");

            while (true) {
                ConsumerRecords<UUID, String> records = consumer.poll(timeout);
                for (ConsumerRecord<UUID, String> record : records) {
                    String result = formattter.format(new Object[]{
                            record.topic(),
                            record.partition(),
                            record.offset(),
                            record.key(),
                            record.value(),
                            consumer.groupMetadata().memberId()
                    });
                    System.out.println(result);

                    ObjectMapper objectMapper = new ObjectMapper();
                    objectMapper.registerModule(new JavaTimeModule());
                    objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
                    objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

                    try {
                        Purchase purchase = objectMapper.readValue(record.value(), Purchase.class);
                        System.out.println("Deserialized Purchase: " + purchase);
                        purchaseManager.makeAPurchase(purchase);
                    } catch (JsonProcessingException e) {
                        System.err.println("Error deserializing record: " + e.getMessage());
                        e.printStackTrace();
                        consumer.seek(new TopicPartition(record.topic(), record.partition()), record.offset());
                    }

                    if (!records.isEmpty()) {
                        consumer.commitSync();
                    }
                }
            }

        } catch (WakeupException e) {
            System.out.println("Consumer woke up or job finished.");
        } catch (Exception e) {
            System.err.println("Error in consumer: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void consumeTopicsByGroup(String name) throws InterruptedException {
        topics = name;
        if (doesTopicExist(topics)) {
            initConsumerGroup();
        } else {
            System.out.println("Topic does not exist: " + topics);
        }
    }

    public boolean doesTopicExist(String topicName) {
        Properties config = new Properties();
        config.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, "kafka1:9192,kafka2:9292,kafka3:9392");

        try (AdminClient adminClient = AdminClient.create(config)) {
            Set<String> topicNames = adminClient.listTopics().names().get();
            return topicNames.contains(topicName);
        } catch (Exception e) {
            System.err.println("Nie udało się sprawdzić istnienia tematu: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
}
