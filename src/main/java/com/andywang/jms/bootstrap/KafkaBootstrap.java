package com.andywang.jms.bootstrap;

import com.andywang.jms.consumer.ConsumerGracefulShutdown;
import com.andywang.jms.consumer.KafkaConsumerDemo;
import com.andywang.jms.producer.KafkaProducerDemo;
import com.andywang.jms.producer.MessageSender;
import com.andywang.jms.producer.PartitionRoundRobin;
import com.andywang.jms.producer.ProducerWithCallback;
import com.andywang.jms.producer.ProducerWithKeys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class KafkaBootstrap implements ApplicationListener<ContextRefreshedEvent> {

//    @Autowired
//    private MessageSender sender;

//    @Autowired
//    private KafkaProducerDemo kafkaProducer;

//    @Autowired
//    private ProducerWithCallback producerWithCallback;

//    @Autowired
//    private PartitionRoundRobin partitionRoundRobin;

    @Autowired
    private ProducerWithKeys producerWithKeys;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {

        // Consumer
//        Thread t = new Thread(new KafkaConsumerDemo());
        Thread t = new Thread(new ConsumerGracefulShutdown());
        t.start();

        // Producer
//        sender.sendMessage("Hello Spring Kafka");
//        kafkaProducer.sendMessage("Hello MyTopic");
//        producerWithCallback.sendMessage("Hello Callback");
//        partitionRoundRobin.sendMessage("Round Robin");
        producerWithKeys.sendMessage("Send with keys");
    }
}
