package org.examples.config;

import org.infinispan.client.hotrod.RemoteCache;
import org.infinispan.client.hotrod.RemoteCacheManager;
import org.infinispan.client.hotrod.configuration.ClientIntelligence;
import org.infinispan.client.hotrod.configuration.ConfigurationBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class InfinispanConfiguration {

    @Bean
    @Primary
    public RemoteCacheManager remoteCacheManager(@Value("${spring.profiles.active}")String profile) {
        System.out.println("Infinispan profile: " + profile);
        ConfigurationBuilder builder = new ConfigurationBuilder();
        builder.connectionTimeout(5000);
        builder.clientIntelligence(ClientIntelligence.BASIC);
        builder.serverFailureTimeout(5000);
        //builder.uri("hotrod://admin:admin@127.0.0.1:11222?auth_realm=default");
        builder.addServer().host("127.0.0.1").port(11222)
                .security().authentication()
                .username("admin").password("admin");
        return new RemoteCacheManager(builder.build());
    }

    @Bean(name = "testCache")
    public RemoteCache<Object, Object> testCache(RemoteCacheManager cacheManager) {
        return cacheManager
                .getCache("testCache");
    }
}
