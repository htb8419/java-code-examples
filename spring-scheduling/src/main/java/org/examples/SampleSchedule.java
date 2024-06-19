package org.examples;

import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.TimeUnit;

@Component
public class SampleSchedule {
    final RestTemplate restTemplate;

    public SampleSchedule(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }
    //@Scheduled(fixedDelay = 5_000)
    public void taskA() throws InterruptedException {
        System.out.println("SampleSchedule.taskA :" + Thread.currentThread().getName());
        Thread.sleep(2_000);
    }

    @Async
    @Scheduled(timeUnit = TimeUnit.SECONDS, fixedRate = 1)
    public void taskB() {
        System.out.println("start SampleSchedule.taskB :" + Thread.currentThread().getName());
        try {
            restTemplate.getForEntity("/_doc/person", String.class);
        } catch (Exception e) {
            System.out.println("sever error");
        }
        System.out.println("finish SampleSchedule.taskB :" + Thread.currentThread().getName());
    }
}
