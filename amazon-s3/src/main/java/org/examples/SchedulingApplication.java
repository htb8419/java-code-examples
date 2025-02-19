package org.examples;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.client.RestTemplate;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.AwsCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;

import java.net.URI;
import java.time.Duration;

@SpringBootApplication
public class SchedulingApplication {

    public static void main(String[] args) {
        SpringApplication.run(SchedulingApplication.class, args);
    }
    
    @Bean
    public S3Client s3Client() {
        String accessKey = "c89cf22b-38be-4db9-a4b7-05298204ae7d";
        String secretKey = "6d786b376ef9e4de2693fcd46abbe91d9523b8645b9f6ef9cd2973bb25e44ca6";
        AwsCredentials credentials = AwsBasicCredentials.create(accessKey, secretKey);
        String endpoint = "https://s3.ir-thr-at1.arvanstorage.ir";
        return S3Client
                .builder()
                .endpointOverride(URI.create(endpoint))
                .region(Region.US_EAST_1)
                .credentialsProvider(StaticCredentialsProvider.create(credentials))
                .build();
    }
}
