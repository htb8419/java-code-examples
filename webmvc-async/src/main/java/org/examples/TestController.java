package org.examples;

import org.springframework.scheduling.annotation.Async;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.ServletWebRequest;

import java.util.concurrent.CompletableFuture;

@RestController
public class TestController {


    @GetMapping("/test")
    @Async("mvcTaskExecutor")
    public CompletableFuture<String> hello(ServletWebRequest webRequest) {
        String mvcThreadName = Thread.currentThread().getName();
        Assert.isTrue(mvcThreadName.startsWith("mvc-task"), "miss configuration");
        System.out.printf("TestController.hello [threadName=%s] \n", mvcThreadName);
        return CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(3_000);
                System.out.printf("CompletableFuture.supplyAsync [threadName=%s] \n", Thread.currentThread().getName());
                Assert.isTrue(webRequest.getRequest().isAsyncStarted(), "webRequest is not async");
                //do anything
                return "hi, dear user";
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
    }
}
