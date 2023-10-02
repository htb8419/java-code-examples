package org.examples.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.listener.KafkaListenerErrorHandler;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.kafka.support.KafkaHeaders;

@Configuration
@EnableKafka
public class KafkaConsumerConfig {

/*    @Bean
    public ConcurrentKafkaListenerContainerFactory<Object, Object> concurrentKafkaListenerContainerFactory( ConsumerFactory<Object, Object> kafkaConsumerFactory) {
        ConcurrentKafkaListenerContainerFactory<Object, Object> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(kafkaConsumerFactory);
        factory.setConcurrency(20);
        factory.getContainerProperties().setAckMode(ContainerProperties.AckMode.MANUAL_IMMEDIATE);
        return factory;
    }*/

    @Bean
    public KafkaListenerErrorHandler consumerErrorHandler() {
        Logger LOGGER = LoggerFactory.getLogger(KafkaListenerErrorHandler.class);
        return (message, exception) -> {
            LOGGER.error(message.getPayload().toString(), exception);
            Acknowledgment acknowledgment = (Acknowledgment) (message.getHeaders().get(KafkaHeaders.ACKNOWLEDGMENT));
            if (acknowledgment != null) {
                acknowledgment.acknowledge();
            }
            return 0;
        };
    }
}
