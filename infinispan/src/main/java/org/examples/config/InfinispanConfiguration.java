package org.examples.config;

import org.infinispan.client.hotrod.DataFormat;
import org.infinispan.client.hotrod.RemoteCache;
import org.infinispan.client.hotrod.RemoteCacheManager;
import org.infinispan.commons.dataconversion.MediaType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class InfinispanConfiguration {
    @Autowired
    RemoteCacheManager remoteCacheManager;

    @Bean
    public RemoteCache<String, String> objectUniqueCache() {
        return remoteCacheManager
                .administration().getOrCreateCache("object-unique", "org.infinispan.LOCAL")
                .withDataFormat(new DataFormat.Builder()
                        .keyType(MediaType.TEXT_PLAIN)
                        .valueType(MediaType.TEXT_PLAIN)
                        .build()
                );
    }
}
