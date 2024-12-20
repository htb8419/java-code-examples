package org.examples.api;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/public/test")
public class TestController {

    @GetMapping
    public ResponseEntity<String> get() {
        return ResponseEntity.ok("Hello World");
    }
}
