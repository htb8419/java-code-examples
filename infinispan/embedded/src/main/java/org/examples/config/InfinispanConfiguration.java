package org.examples.config;

import org.infinispan.Cache;
import org.infinispan.commons.api.CacheContainerAdmin;
import org.infinispan.configuration.cache.ConfigurationBuilder;
import org.infinispan.configuration.global.GlobalConfiguration;
import org.infinispan.configuration.global.GlobalConfigurationBuilder;
import org.infinispan.manager.DefaultCacheManager;
import org.infinispan.manager.EmbeddedCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

@Configuration
public class InfinispanConfiguration {

    @Bean
    public EmbeddedCacheManager infinispanCacheManager() {
        GlobalConfiguration globalConfiguration = GlobalConfigurationBuilder.defaultClusteredBuilder().build();
        return new DefaultCacheManager(globalConfiguration);
    }
    @Bean(name = "uniqueObjectCache")
    public Cache<String, Object> uniqueObjectCache(EmbeddedCacheManager cacheManager) throws IOException {
        org.infinispan.configuration.cache.Configuration cacheConfiguration = new ConfigurationBuilder()
                .expiration().lifespan(60, TimeUnit.SECONDS)
                .memory().maxCount(50_000)
                //.transaction().use1PcForAutoCommitTransactions(true)
                //.transaction().lockingMode(LockingMode.OPTIMISTIC)
                .build();
        return cacheManager.administration()
                .withFlags(CacheContainerAdmin.AdminFlag.VOLATILE)
                .getOrCreateCache("uniqueObjectCache", cacheConfiguration);
    }
}
