package org.examples;

import org.examples.model.CarInfo;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;

import java.util.Date;
import java.util.UUID;
import java.util.function.Supplier;

@Component
public class CarInfoProducer {
    private final KafkaTemplate<String, CarInfo> kafkaTemplate;
    CarInfoSupplier carInfoSupplier = new CarInfoSupplier();

    public CarInfoProducer(KafkaTemplate<String, CarInfo> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @Scheduled(initialDelay = 10000, fixedDelay = 5000)
    public void run() {
        final CarInfo carInfo = carInfoSupplier.get();
        final ListenableFuture<SendResult<String, CarInfo>> resultListenableFuture = kafkaTemplate.send("car-info", carInfo.getPlateNumber(), carInfo);
        resultListenableFuture.addCallback(result -> {
            System.out.println("success send message");
        }, ex -> {
            System.out.println("failed send message");
        });
    }

    static class CarInfoSupplier implements Supplier<CarInfo> {

        @Override
        public CarInfo get() {
            int roadId = (int) (Math.random() * (1000 - 10)) + 10;
            int speed = (int) (Math.random() * (200 - 60)) + 60;
            return new CarInfo(roadId, speed, UUID.randomUUID().toString(), new Date());
        }
    }
}
