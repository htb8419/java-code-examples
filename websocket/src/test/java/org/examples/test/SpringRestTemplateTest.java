package org.examples.test;

import org.examples.model.Person;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class SpringRestTemplateTest {
    private final static int EXPECTED_PORT = 8080;
    @Autowired
    private RestTemplate restTemplate;
    @Value("${server.port:8080}")
    private int serverPort;

    @Test
    public void whenApplicationIsRunning_thenServerPortIsExpected() {
        Assertions.assertEquals(EXPECTED_PORT, serverPort);
    }

    @Test
    public void whenPostObject_thenResponseOk() throws ExecutionException, InterruptedException, TimeoutException {
        String serverUrl = "http://localhost:8080/person/save";
        Person person = new Person("ali");
        CompletableFuture<String> completableFuture = CompletableFuture.supplyAsync(() -> {
            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.CONTENT_TYPE, "application/json");
            HttpEntity<Object> request = new HttpEntity<>(person, headers);
            ResponseEntity<String> response = restTemplate.postForEntity(serverUrl, request, String.class);
            return response.getBody();
        });
        Assertions.assertEquals("Saved Person[name=ali]", completableFuture.get(10, TimeUnit.SECONDS));
    }
}
