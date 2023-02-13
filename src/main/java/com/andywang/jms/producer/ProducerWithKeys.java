package com.andywang.jms.producer;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Properties;

@Slf4j
@Component
public class ProducerWithKeys {

    public static final String KEY_TOPIC = "key_topic";

    @Value(value = "${spring.kafka.bootstrap-servers}")
    private String bootstrapAddress;

    @SneakyThrows
    public void sendMessage(String message) {

        Properties properties = new Properties();
        properties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
        properties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());


        // Create producer
        try (KafkaProducer<String, String> kafkaProducer = new KafkaProducer<>(properties)) {

            for (int j = 0; j < 2; j++) {

                for (int i = 0; i < 10; i++) {

                    String key = "id_" + i;
                    String value = message + i;

                    // Create producer record
                    ProducerRecord<String, String> producerRecord = new ProducerRecord<>(KEY_TOPIC, key, value);

                    // Send message
                    kafkaProducer.send(producerRecord, (recordMetadata, e) -> {
                        if (e == null) {
                            // message sent successfully
                            log.info("Partition: " + recordMetadata.partition());
                            log.info("Key: " + key);
                            // MESSAGES WITH SAME ID ASSIGN TO SAME PARTITION
                        } else {
                            log.error("Message sent fail: ", e);
                        }
                    });
                }
            }

            // Tell producer to send all message and block until done (synchronous)
            kafkaProducer.flush();
        }
    }
}
