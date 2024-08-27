package org.examples.config;

import org.infinispan.Cache;
import org.infinispan.client.hotrod.RemoteCacheManager;
import org.infinispan.client.hotrod.configuration.ClientIntelligence;
import org.infinispan.client.hotrod.configuration.ConfigurationBuilder;
import org.infinispan.manager.DefaultCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

@Configuration
public class InfinispanConfiguration {

    @Bean
    public RemoteCacheManager acheManager() {
        ConfigurationBuilder builder = new ConfigurationBuilder();
        builder.memory().maxCount(10_000);
        //builder. ("hotrod://admin:secret@localhost:11222");
        builder.expiration().lifespan(60, TimeUnit.SECONDS);

        return new RemoteCacheManager(builder.build());
    }

    @Bean(name = "uniqueObjectCache")
    public Cache<String, Object> uniqueObjectCache(RemoteCacheManager acheManager) throws IOException {
        org.infinispan.configuration.cache.Configuration cacheConfiguration = new ConfigurationBuilder()
                .expiration().lifespan(60, TimeUnit.SECONDS)
                .memory().maxCount(10_000)
                .transaction().use1PcForAutoCommitTransactions(true)
                //.transaction().lockingMode(LockingMode.OPTIMISTIC)
                .build();
        return cacheManager.createCache("uniqueObjectCache", cacheConfiguration);
    }
}
