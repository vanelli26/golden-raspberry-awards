package br.com.gra.config;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

@Configuration
@EnableCaching
@EnableScheduling
public class CacheConfig {

    public static final String INTERVAL = "interval";

    @Bean
    public CacheManager cacheManager() {
        return new ConcurrentMapCacheManager(INTERVAL);
    }
}
