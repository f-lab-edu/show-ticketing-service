package com.show.showticketingservice.config;

import com.show.showticketingservice.tool.constants.CacheConstant;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class RedisCacheConfig {

    @Value("${spring.redis.cache.host}")
    private String cacheHost;

    @Value("${spring.redis.cache.port}")
    private int cachePort;

    @Bean("redisCacheConnectionFactory")
    public RedisConnectionFactory redisCacheConnectionFactory() {
        return new LettuceConnectionFactory(new RedisStandaloneConfiguration(cacheHost, cachePort));
    }

    /*
        CacheManager:
        - 스프링의 중앙 캐시 관리자 SPI(Service Provider Interface)로, 명명된 캐시 영역을 가져온다.

        RedisCacheManager:
        - Redis를 이용하여 캐싱을 적용할 땐 이 클래스로 CacheManager를 지원한다
        - RedisCacheConfiguration의 기본 구성과 다른 구성이 필요한 캐시는
          RedisCacheManager.RedisCacheManagerBuilder.withInitialCacheConfigurations(Map) 를 통해 지정 가능

        CacheResolver:
        - 캐시를 지원하는 resolver
        - 인터셉트 된 메서드 호출에 사용할 캐시 인스턴스 결정
        - CacheManager를 사용하여 탐색할 캐시 초기화
     */
    @Bean
    public RedisCacheManager redisCacheManager(@Qualifier("redisCacheConnectionFactory") RedisConnectionFactory redisConnectionFactory) {

        RedisCacheConfiguration redisCacheConfig = RedisCacheConfiguration.defaultCacheConfig()
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(new GenericJackson2JsonRedisSerializer()));

        Map<String, RedisCacheConfiguration> redisCacheConfigMap = new HashMap<>();
        redisCacheConfigMap.put(CacheConstant.VENUE, redisCacheConfig.entryTtl(Duration.ofHours(2L)));

        return RedisCacheManager.builder(redisConnectionFactory)
                .withInitialCacheConfigurations(redisCacheConfigMap)
                .build();
    }

}
