package org.examples.ratelimit;

import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

import java.time.Duration;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@SpringBootApplication
@EnableCaching
public class RateLimitApplication {

    public static void main(String[] args) {
        SpringApplication.run(RateLimitApplication.class, args);
        /*UserRequest userRequest = new UserRequest("John", "12.0.5.66");
        Map<UserRequest, Bucket> userOrderBucketBuckets = new ConcurrentHashMap<>();
        Bucket bucket = userOrderBucketBuckets.computeIfAbsent(userRequest, s -> newBucket(2));

        for (int i = 0; i < 10 && bucket.tryConsume(1); i++) {
            System.out.println("do request >> " + i);
        }*/
    }

    static Bucket newBucket(int token) {
        return Bucket.builder().addLimit(
                Bandwidth.builder().capacity(token).refillGreedy(token, Duration.ofMinutes(1)).build()
        ).build();
    }

    public record UserRequest(String username, String ipAddress) {

    }

}