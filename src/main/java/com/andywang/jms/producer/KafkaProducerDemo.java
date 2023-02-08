package com.andywang.jms.producer;

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
public class KafkaProducerDemo {

    public static final String MY_TOPIC = "my_topic";

    @Value(value = "${spring.kafka.bootstrap-servers}")
    private String bootstrapAddress;

    private ProducerRecord<String, String> producerRecord;

    public void sendMessage(String message) {

        Properties properties = new Properties();
        properties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
        properties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());


        // Create producer
        try (KafkaProducer<String, String> kafkaProducer = new KafkaProducer<>(properties)) {
            // Create producer record
            producerRecord = new ProducerRecord<>(MY_TOPIC, message);

            // Send message
            kafkaProducer.send(producerRecord);

            log.info("Sent Message: " + message);

            // Tell producer to send all message and block until done (synchronous)
            kafkaProducer.flush();

//            kafkaProducer.close();
        }
    }
}
