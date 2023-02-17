package com.andywang.jms.bootstrap;

import com.andywang.jms.consumer.ConsumerCooperative;
import com.andywang.jms.consumer.ConsumerGracefulShutdown;
import com.andywang.jms.consumer.ConsumerWithThread;
import com.andywang.jms.consumer.KafkaConsumerDemo;
import com.andywang.jms.producer.CustomMessageSender;
import com.andywang.jms.producer.KafkaProducerDemo;
import com.andywang.jms.producer.MessageSender;
import com.andywang.jms.producer.PartitionRoundRobin;
import com.andywang.jms.producer.ProducerWithCallback;
import com.andywang.jms.producer.ProducerWithKeys;
import com.andywang.jms.vo.CustomMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.util.Date;

@Slf4j
@Component
public class KafkaBootstrap implements ApplicationListener<ContextRefreshedEvent> {

    @Autowired
    private MessageSender sender;

//    @Autowired
//    private KafkaProducerDemo kafkaProducer;

//    @Autowired
//    private ProducerWithCallback producerWithCallback;

//    @Autowired
//    private PartitionRoundRobin partitionRoundRobin;

//    @Autowired
//    private ProducerWithKeys producerWithKeys;

    @Autowired
    private CustomMessageSender customMessageSender;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {

        // Consumer
//        Thread t = new Thread(new KafkaConsumerDemo());
//        Thread t = new Thread(new ConsumerCooperative());
//        ConsumerWithThread consumer = new ConsumerWithThread();
//        Thread t = new Thread(new ConsumerWithThread());
//        t.start();

        // Producer
//        sender.sendMessage("Hello Spring Kafka");
//        kafkaProducer.sendMessage("Hello MyTopic");
//        producerWithCallback.sendMessage("Hello Callback");
//        partitionRoundRobin.sendMessage("Round Robin");
//        producerWithKeys.sendMessage("Send with keys");
        customMessageSender.sendCustomMessage(CustomMessage.builder().message("I'm customized").timestamp(new Date()).build());
    }
}
