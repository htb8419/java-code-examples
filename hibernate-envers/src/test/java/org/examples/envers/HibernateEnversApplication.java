package org.examples.envers;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
public class HibernateEnversApplication {
    public static void main(String[] args) {
        SpringApplication.run(HibernateEnversApplication.class, args);
    }

}
