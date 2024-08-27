package org.examples;


import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.concurrent.ConcurrentHashMap;

@SpringBootApplication
@EnableCaching
public class RedisSampleApplication {

    public static void main(String[] args) {
        //SpringApplication.run(RedisSampleApplication.class,args);
        ConcurrentHashMap<String,String> obj=new ConcurrentHashMap<>(10);
        System.out.println(obj.put("k1","v1"));
        System.out.println(obj.put("k1","v2"));
        System.out.println(obj.get("k1"));

    }
    @Bean
    JedisConnectionFactory jedisConnectionFactory() {
        JedisConnectionFactory jedisConnectionFactory = new JedisConnectionFactory();
        jedisConnectionFactory.setHostName("192.168.11.130");
        jedisConnectionFactory.setPort(6379);
        return jedisConnectionFactory;
    }

    @Bean
    public RedisTemplate<String, Object> redisTemplate() {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(jedisConnectionFactory());
        return template;
    }
    @Bean
    public CommandLineRunner test(StudentRepository studentRepository){
        return args -> {
            Student hadi = studentRepository.save(new Student("1", "hadi", Student.Gender.MALE, 19));
            System.out.println(hadi);
        };
    }
}
