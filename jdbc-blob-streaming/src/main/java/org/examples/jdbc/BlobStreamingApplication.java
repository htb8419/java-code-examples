package org.examples.jdbc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement(proxyTargetClass = true)
public class BlobStreamingApplication {
    public static void main(String[] args) {
        SpringApplication.run(BlobStreamingApplication.class, args);
    }
}
