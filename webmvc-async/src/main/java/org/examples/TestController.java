package org.examples;

import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.ServletWebRequest;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

@RestController
public class TestController {

    @GetMapping("/api/test")
    @Async("mvcTaskExecutor")
    public CompletableFuture<ResponseEntity<String>> hello(ServletWebRequest webRequest) {
        String mvcThreadName = Thread.currentThread().getName();
        Assert.isTrue(mvcThreadName.startsWith("mvc-task"), "miss configuration");
        System.out.printf("TestController.hello [threadName=%s] \n", mvcThreadName);

        return CompletableFuture.supplyAsync(() -> {
            try {
                System.out.printf("CompletableFuture.supplyAsync [threadName=%s] \n", Thread.currentThread().getName());
                Thread.sleep(1_000);
                //do anything
                return ResponseEntity.ok("hi, dear user");
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }).orTimeout(3, TimeUnit.SECONDS);
    }
}
