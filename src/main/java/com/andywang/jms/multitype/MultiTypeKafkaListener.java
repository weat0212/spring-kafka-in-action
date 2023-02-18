package com.andywang.jms.multitype;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
//@KafkaListener(id = "multiGroup", topics = MultiTypeProducer.MULTI_TYPE_TOPIC)
@KafkaListener(id = "multiGroup", topics = MultiTypeProducer.MULTI_TYPE_TOPIC, containerFactory = "multiTypeKafkaListenerContainerFactory")
public class MultiTypeKafkaListener {

    @KafkaHandler
    public void handleTypeOne(TypeOne two) {
        log.info("Type One handler received: " + two);
    }

    @KafkaHandler
    public void handleTypeTwo(TypeTwo one) {
        log.info("Type Two handler received: " + one);
    }

    @KafkaHandler(isDefault = true)
    public void unknown(Object object) {
        log.info("Unknown type received: " + object);
    }
}
