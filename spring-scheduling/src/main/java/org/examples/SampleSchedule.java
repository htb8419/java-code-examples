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

    @Scheduled(cron = "20 * * * * *")
    public void taskA() {

    }

    @Async
    @Scheduled(timeUnit = TimeUnit.SECONDS, fixedRate = 1)
    public void taskB() {
        String threadName = Thread.currentThread().getName();
        System.out.println("start SampleSchedule.taskB :" + threadName);
        try {
            restTemplate.getForEntity("/_doc/person", String.class);
        } catch (Exception e) {
            System.out.println("sever error");
        }
        System.out.println("finish SampleSchedule.taskB :" + threadName);
    }
}
