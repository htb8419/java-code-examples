package org.examples;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
public class DemoController {

    @GetMapping("/demo")
    public String getTime() throws InterruptedException {
        int waiting = (int) (Math.random() * (10_000 - 100)) + 100;
        Thread.sleep(waiting);
        return "currentTime: "+new Date();
    }
}
