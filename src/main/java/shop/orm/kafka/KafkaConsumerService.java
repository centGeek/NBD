package shop.orm.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.apache.kafka.clients.admin.*;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.KafkaFuture;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.errors.WakeupException;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.UUIDDeserializer;
import shop.orm.menagers.PurchaseManager;
import shop.orm.model.Purchase;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class KafkaConsumerService {
    private static final List<KafkaConsumer<UUID, String>> consumerGroup = new ArrayList<>();
    private static final PurchaseManager purchaseManager = new PurchaseManager();
    private static String topics;

    public static void initConsumerGroup() {
        Properties consumerConfig = new Properties();
        consumerConfig.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, UUIDDeserializer.class.getName());
        consumerConfig.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        consumerConfig.put(ConsumerConfig.GROUP_ID_CONFIG, "grupa");
        consumerConfig.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "kafka1:9192,kafka2:9292,kafka3:9392");
        consumerConfig.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "true");

        for(int i = 0; i < 2; i++) {
            KafkaConsumer<UUID, String> consumer = new KafkaConsumer<>(consumerConfig);
            consumer.subscribe(List.of(topics));
            consumerGroup.add(consumer);
        }
    }

    private static void consume(KafkaConsumer<UUID, String> consumer) {
        try {
            System.out.println("Starting poll...");
            long startTime = System.currentTimeMillis();
            consumer.poll(0);
            System.out.println("Poll took: " + (System.currentTimeMillis() - startTime) + "ms");
            Set<TopicPartition> consumerAssigment = consumer.assignment();
            System.out.println("Assigned partitions: " + consumerAssigment);

            Duration timeout = Duration.of(100, ChronoUnit.MILLIS);

            while (true) {
                ConsumerRecords<UUID, String> records = consumer.poll(timeout);
                for (ConsumerRecord<UUID, String> record : records) {

                    ObjectMapper objectMapper = new ObjectMapper();
                    objectMapper.registerModule(new JavaTimeModule());
                    objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
                    objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
                    try {
                        Purchase purchase = objectMapper.readValue(record.value(), Purchase.class);
                        System.out.println("Deserialized Purchase: " + purchase);
                        purchaseManager.makeAPurchase(purchase);
                        System.out.println("Amount of purchases by given client " + purchaseManager.getAllPurchasesByClient(purchase.getClient());
                    } catch (JsonProcessingException e) {
                        System.err.println("Error deserializing record: " + e.getMessage());
                        e.printStackTrace();
                        consumer.seek(new TopicPartition(record.topic(), record.partition()), record.offset());
                    }

//                    if (!records.isEmpty()) {
//                        consumer.commitSync();
//                    }
                }
            }

        } catch (WakeupException e) {
            System.out.println("Consumer woke up or job finished.");
        } catch (Exception e) {
            System.err.println("Error in consumer: " + e.getMessage());
            e.printStackTrace();
        }
    }
    public static void createTopic (String topic) throws InterruptedException {
        System.out.println("Creating topic " + topic);
        Properties properties = new Properties();
        properties.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, "kafka1:9192, kafka1:9292, kafka1:9392");
        int partitionsNumber = 3;
        short replicationFactor = 3;
        try (Admin admin = Admin.create(properties)) {
            NewTopic newTopic = new NewTopic(topic, partitionsNumber, replicationFactor);
            CreateTopicsOptions options = new CreateTopicsOptions()
                    .timeoutMs(1000)
                    .validateOnly(false)
                    .retryOnQuotaViolation(true);
            CreateTopicsResult result = admin.createTopics(List.of(newTopic), options);
            KafkaFuture<Void> futureResult = result.values().get(topic);
            futureResult.get();
            System.out.println(topic + " is created");
        } catch (ExecutionException ee) {
            System.out.println(ee.getCause());
        }
    }

    public void consumeTopicsByGroup(String name) throws InterruptedException {
        topics = name;
        initConsumerGroup();
        try (ExecutorService executorService = Executors.newFixedThreadPool(2)) {
            for (KafkaConsumer<UUID, String> consumer : consumerGroup) {
                executorService.execute(() -> consume(consumer));
            }
            Thread.sleep(100);
//            for (KafkaConsumer<UUID, String> consumer : consumerGroup) {
//                consumer.wakeup();
//            }
        }
    }
}