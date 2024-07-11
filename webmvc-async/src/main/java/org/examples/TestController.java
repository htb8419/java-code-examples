package org.examples;

import org.springframework.scheduling.annotation.Async;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.concurrent.CompletableFuture;

@RestController
public class TestController {

    @Async
    @GetMapping("/test")
    public CompletableFuture<String> hello(HttpServletRequest request) {
        Assert.isTrue(request.isAsyncStarted(),"request is not async");
        String name = Thread.currentThread().getName();
        Assert.isTrue(name.startsWith("mvc-task"), "miss configuration");
        System.out.println("threadName :" + name);
        return CompletableFuture.supplyAsync(() -> {
            //do anything
            return "hi, dear user";
        });
    }
}
