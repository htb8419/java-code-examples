package org.examples.notification;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

@Configuration
public class WebPushConfig {

    //@Bean
    public PushNotificationService notificationService(
            @Value("${fixa.notification.private-key-location}") final RSAPrivateKey privateKey,
            @Value("${fixa.notification.public-key-location}") final RSAPublicKey publicKey,
            @Value("${fixa.notification.subject}") final String subject) {
        return new PushNotificationService(privateKey, publicKey, subject);
    }
    @Bean
    public PushNotificationService notificationService() {
        return new PushNotificationService(null,null,"mailto:your-email@example.com");
    }
}
