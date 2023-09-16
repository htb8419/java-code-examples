package org.examples;

import org.examples.model.CarInfo;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class CarInfoConsumer {

    @KafkaListener(topics = "car-info", groupId = "test_0",concurrency ="1")
    public void processCarInfo(@Payload CarInfo record) {
        //acknowledgment.acknowledge();
        System.out.println(Thread.currentThread().getId() + "  " + Thread.currentThread().getName() + "  "
                + Thread.currentThread().getThreadGroup().getName() + "  " + record.getTakePictureTime());
    }
}
