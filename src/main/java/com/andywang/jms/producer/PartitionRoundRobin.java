package com.andywang.jms.producer;

import com.andywang.jms.decorator.ToStringDecorator;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.clients.producer.RoundRobinPartitioner;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Properties;

@Slf4j
@Component
public class PartitionRoundRobin {

    public static final String CALLBACK_TOPIC = "callback_topic";

    @Value(value = "${spring.kafka.bootstrap-servers}")
    private String bootstrapAddress;

    @SneakyThrows
    public void sendMessage(String message) {

        Properties properties = new Properties();
        properties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
        properties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.setProperty(ProducerConfig.BATCH_SIZE_CONFIG, "50");
        properties.setProperty(ProducerConfig.PARTITIONER_CLASS_CONFIG, RoundRobinPartitioner.class.getName());


        // Create producer
        try (KafkaProducer<String, String> kafkaProducer = new KafkaProducer<>(properties)) {

            for (int j = 0; j < 10; j++) {
                for (int i = 0; i < 30; i++) {
                    // Create producer record
                    ProducerRecord<String, String> producerRecord = new ProducerRecord<>(CALLBACK_TOPIC, message + i);

                    // Send message
                    kafkaProducer.send(producerRecord, (recordMetadata, e) -> {
                        if (e == null) {
                            // message sent successfully
                            log.info("Received metadata: \n" + new ToStringDecorator<RecordMetadata>(recordMetadata).toString());
                            log.info("Partition: " + recordMetadata.partition());
                        } else {
                            log.error("Message sent fail: ", e);
                        }
                    });
                }

                Thread.sleep(500);
            }

            // Tell producer to send all message and block until done (synchronous)
            kafkaProducer.flush();
        }
    }
}
