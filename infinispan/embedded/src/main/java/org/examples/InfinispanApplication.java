package org.examples;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;

import java.time.Duration;
import java.util.Objects;

@SpringBootApplication
@EnableCaching
public class InfinispanApplication {

    public static void main(String[] args) {
        SpringApplication.run(InfinispanApplication.class, args);
    }

    //@Bean
    public CommandLineRunner objectCreator(UniqueObjectControlService uniqueObjectControlService) {
        return args -> {
            Person hadi = new Person("hadi", "tayebi", "1861274629", 40);
            Person ali = new Person("ali", "marandi", "4240170494", 33);
            uniqueObjectControlService.addObject(hadi);
            uniqueObjectControlService.addObject(ali);
            //uniqueObjectControlService.evict(hadi);
            System.out.printf("contents hadi %s ",uniqueObjectControlService.contains(hadi));
            uniqueObjectControlService.addObject(hadi, Duration.ofSeconds(13));
            uniqueObjectControlService.addObject(hadi, Duration.ofSeconds(15));
            uniqueObjectControlService.printAll();
        };
    }

    record Person(String name,String family,String uniqueCode,int age){

        @Override
        public boolean equals(Object object) {
            if (this == object) return true;
            if (object == null || getClass() != object.getClass()) return false;
            Person person = (Person) object;
            return age == person.age && Objects.equals(name, person.name) && Objects.equals(family, person.family) && Objects.equals(uniqueCode, person.uniqueCode);
        }

        @Override
        public int hashCode() {
            return Objects.hash(name, family,uniqueCode, age);
        }
    }
}
