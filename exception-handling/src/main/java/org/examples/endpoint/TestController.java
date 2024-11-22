package org.examples.endpoint;


import org.examples.exception.BusinessException;
import org.springframework.http.CacheControl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Duration;

@RestController
@RequestMapping("/test")
public class TestController {

    @GetMapping("/businessException")
    public ResponseEntity<?> throwBusinessException() {
        throw new BusinessException("business role failed.");
    }
    @GetMapping("/unhandledException")
    public ResponseEntity<?> throwUnhandledException() {
        throw new RuntimeException("error");
    }
}
