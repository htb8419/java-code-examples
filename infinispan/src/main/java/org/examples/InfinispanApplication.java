package org.examples;

import org.infinispan.client.hotrod.RemoteCache;
import org.infinispan.commons.marshall.UTF8StringMarshaller;
import org.infinispan.spring.starter.remote.InfinispanRemoteCacheCustomizer;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@SpringBootApplication
@EnableAsync
@EnableScheduling
public class InfinispanApplication {

    public static void main(String[] args) {
        SpringApplication.run(InfinispanApplication.class,args);
        ConcurrentMap<String,String> concurrentMap=new ConcurrentHashMap<>();
        System.out.println(concurrentMap.put("k","val0"));
        System.out.println(concurrentMap.put("k","val1"));
    }
    //@Bean
    @Order(Ordered.HIGHEST_PRECEDENCE)
    public InfinispanRemoteCacheCustomizer caches() {
        return b -> {
            b.remoteCache("object-unique").marshaller(new UTF8StringMarshaller());
        };
    }
    @Bean
    public CommandLineRunner objectCreator(RemoteCache<String,String> objectUniqueCache){
        return args -> {
            objectUniqueCache.putIfAbsent("key1","val1");
            objectUniqueCache.putIfAbsent("key1","val2");
        };
    }
}
