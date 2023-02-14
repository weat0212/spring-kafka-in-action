package com.andywang.jms.consumer;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.errors.WakeupException;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.time.Duration;
import java.util.List;
import java.util.Properties;

import static com.andywang.jms.producer.ProducerWithKeys.KEY_TOPIC;

@Slf4j
public class ConsumerGracefulShutdown implements Runnable {

    private static final String GROUP_ID = "my-group0";

    @Override
    public void run() {

        Properties properties = new Properties();

        // Create consumer configs
        properties.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        properties.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        properties.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        properties.setProperty(ConsumerConfig.GROUP_ID_CONFIG, GROUP_ID);
        properties.setProperty(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "latest");

        // Create consumer
        try (KafkaConsumer<String, String> consumer = new KafkaConsumer<>(properties)) {

            final Thread mainThread = Thread.currentThread();
            Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                log.info("Detected a shutdown, let's exit by calling consumer.wakeup()");
                consumer.wakeup();

                // join the main thread to allow the execution of the code
                try {
                    mainThread.join();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }));

            consumer.subscribe(List.of(KEY_TOPIC));

            while (true) {
                log.info("Polling...");
                ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(1000));
                for (ConsumerRecord<String, String> record : records) {
                    log.info(String.format("Key: %s, Value: %s", record.key(), record.value()));
                    log.info(String.format("Partition: %s, Offset: %s", record.partition(), record.offset()));
                }
            }
        } catch (WakeupException ex) {
            log.info("Consumer is starting to shutdown");
        }
    }
}
