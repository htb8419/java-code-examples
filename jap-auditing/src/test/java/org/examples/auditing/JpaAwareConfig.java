package org.examples.auditing;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Configuration
@EnableJpaAuditing
public class JpaAwareConfig {

    @Bean
    public AuditorAware<Integer> auditorAware() {
        return () -> {
            System.out.println("return current auditor");
            return java.util.Optional.of(1);
        };//;
    }
}
