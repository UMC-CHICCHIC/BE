package chic_chic.spring.config.properties;

import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCache;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;
import java.util.List;

@Configuration
@EnableCaching
@ConditionalOnProperty(name = "spring.cache.type", havingValue = "caffeine", matchIfMissing = true)
public class CacheConfig {

    @Bean
    public CacheManager cacheManager() {
        CaffeineCache productDetail = new CaffeineCache(
                "product:detail",
                Caffeine.newBuilder()
                        .expireAfterWrite(Duration.ofMinutes(10)) // 상세 10분
                        .maximumSize(1_000)
                        .build());

        CaffeineCache reviews = new CaffeineCache(
                "reviews",
                Caffeine.newBuilder()
                        .expireAfterWrite(Duration.ofMinutes(3))  // 리뷰 3분
                        .maximumSize(10_000)
                        .build());

        SimpleCacheManager manager = new SimpleCacheManager();
        manager.setCaches(List.of(productDetail, reviews));
        return manager;
    }
}