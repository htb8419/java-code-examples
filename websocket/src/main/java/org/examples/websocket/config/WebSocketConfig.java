package org.examples.websocket.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.ImmutableMessageChannelInterceptor;
import org.springframework.web.socket.config.annotation.*;

import java.util.concurrent.TimeUnit;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        StompWebSocketEndpointRegistration socketEndpointRegistration = registry.addEndpoint("/websocket");
        socketEndpointRegistration.setAllowedOrigins("*");
        socketEndpointRegistration
                //.setAllowedOriginPatterns("*");
                .withSockJS()
                .setWebSocketEnabled(true)
                .setHeartbeatTime(3000)
                .setDisconnectDelay(1000)
                ;
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry brokerRegistry) {
        //brokerRegistry.enableSimpleBroker("/queue/");
        brokerRegistry.setApplicationDestinationPrefixes("/app")
                .setPreservePublishOrder(true)
                .setUserRegistryOrder(1);
    }

    @Override
    public void configureWebSocketTransport(WebSocketTransportRegistration registration) {
        registration.setSendTimeLimit((int) TimeUnit.SECONDS.toMicros(20));
    }

    @Override
    public void configureClientInboundChannel(ChannelRegistration registration) {
        registration.interceptors(inboundChannelInterceptor());
    }

    @Bean
    public ChannelInterceptor inboundChannelInterceptor() {
        //TODO
        return new ImmutableMessageChannelInterceptor();
    }
}
