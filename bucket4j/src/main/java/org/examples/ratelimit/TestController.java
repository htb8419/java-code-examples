package org.examples.ratelimit;

import com.giffing.bucket4j.spring.boot.starter.context.RateLimiting;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @GetMapping("/hello")
    @RateLimiting(name = "test-bucket4j",
            ratePerMethod = true,
            fallbackMethodName = "requestLimited"
    )
    public ResponseEntity<?> hello() {
        return ResponseEntity.ok("hello");
    }
    public ResponseEntity<?> requestLimited() {
        return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS).build();
    }
}
