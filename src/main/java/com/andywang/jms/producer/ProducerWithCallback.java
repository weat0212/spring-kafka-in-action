package com.andywang.jms.producer;

import com.andywang.jms.decorator.ToStringDecorator;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Properties;

@Slf4j
@Component
public class ProducerWithCallback {

    public static final String MY_TOPIC = "my_topic";

    @Value(value = "${spring.kafka.bootstrap-servers}")
    private String bootstrapAddress;

    public void sendMessage(String message) {

        Properties properties = new Properties();
        properties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
        properties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());


        // Create producer
        try (KafkaProducer<String, String> kafkaProducer = new KafkaProducer<>(properties)) {
            // Create producer record
            ProducerRecord<String, String> producerRecord = new ProducerRecord<>(MY_TOPIC, message);

            // Send message
            kafkaProducer.send(producerRecord, new Callback() {
                @Override
                public void onCompletion(RecordMetadata recordMetadata, Exception e) {
                    if (e == null) {
                        // message sent successfully
                        log.info("Received metadata: \n" + new ToStringDecorator<RecordMetadata>(recordMetadata).toString());
                    } else {
                        log.error("Message sent fail: ", e);
                    }
                }
            });

            log.info("Sent Message: " + message);

            // Tell producer to send all message and block until done (synchronous)
            kafkaProducer.flush();
        }
    }
}
