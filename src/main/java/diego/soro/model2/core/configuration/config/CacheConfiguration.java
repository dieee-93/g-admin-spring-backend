package diego.soro.model2.core.configuration.config;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Cache configuration for CompanyConfiguration service
 *
 * Provides in-memory caching for frequently accessed configuration data
 * to improve performance of feature toggle operations.
 */
@Configuration
@EnableCaching
public class CacheConfiguration {

    /**
     * Cache manager for company configurations
     * Uses ConcurrentMapCacheManager for simplicity in Lite tier
     *
     * For Pro/Enterprise tiers, this could be replaced with Redis
     */
    @Bean
    public CacheManager cacheManager() {
        ConcurrentMapCacheManager cacheManager = new ConcurrentMapCacheManager();

        // Pre-configure cache names used by ConfigurationService
        cacheManager.setCacheNames(java.util.List.of(
                "company-config",
                "module-status"
        ));

        // Optional: Set cache settings
        cacheManager.setAllowNullValues(false);

        return cacheManager;
    }
}