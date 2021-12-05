package com.alopezme.embeddedbooks.configuration;


import com.alopezme.embeddedbooks.model.Book;
import org.infinispan.configuration.cache.*;
import org.infinispan.configuration.global.GlobalConfigurationBuilder;
import org.infinispan.spring.starter.embedded.InfinispanCacheConfigurer;
import org.infinispan.spring.starter.embedded.InfinispanGlobalConfigurer;
import org.springframework.context.annotation.Bean;

@org.springframework.context.annotation.Configuration
public class InfinispanConfiguration {

    @Bean
    public InfinispanGlobalConfigurer globalCustomizer() {
        return () -> GlobalConfigurationBuilder.defaultClusteredBuilder().build();
    }

    @Bean
    public InfinispanCacheConfigurer cacheConfigurer() {
        return manager -> {
            final Configuration ispnConfig = new ConfigurationBuilder()
                    .clustering()
                    .cacheMode(CacheMode.REPL_SYNC)
                    .build();


            manager.defineConfiguration("sessions", ispnConfig);
        };
    }

    /**
     * Create caches programmatically using the following Bean
     * @return
     */
    @Bean(name = "books")
    public org.infinispan.configuration.cache.Configuration smallCache() {
        return new ConfigurationBuilder()
                .clustering()
                    .cacheMode(CacheMode.DIST_SYNC)
//                .encoding()
//                    .key().mediaType("application/x-protostream")
//                .encoding()
//                    .value().mediaType("application/x-protostream")
                .indexing()
                    .enable()
                        .addIndexedEntity(Book.class)
                    // Set provider to heap to simplify running several instances in local
                    .storage(IndexStorage.LOCAL_HEAP)
                .memory()
                    .storage(StorageType.HEAP)
//                    .maxSize("100MB")
//                    .whenFull(EvictionStrategy.REMOVE)
                .statistics()
                    .enable()
                .build();
    }
}
