package org.examples;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @GetMapping("/test/{name}")
    @Cacheable(cacheNames = "defaultCache")
    public String get(@PathVariable String name) {
        return "Hello "+name;
    }
}
