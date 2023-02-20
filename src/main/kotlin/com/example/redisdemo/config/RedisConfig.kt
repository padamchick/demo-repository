package com.example.redisdemo.config

import io.github.bucket4j.Bucket
import io.github.bucket4j.distributed.proxy.ProxyManager
import io.github.bucket4j.grid.jcache.JCacheProxyManager
import org.redisson.Redisson
import org.redisson.api.RedissonClient
import org.redisson.config.Config
import org.redisson.jcache.configuration.RedissonConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import javax.cache.CacheManager
import javax.cache.Caching
import javax.cache.configuration.MutableConfiguration

@Configuration
class RedisConfig {
    @Bean
    fun config(): RedissonClient {
        val config = Config()
        config.useSingleServer().address = "redis://localhost:6379"
        return Redisson.create(config)
    }

    @Bean
    fun cacheManager(redissonClient: RedissonClient): CacheManager {
        val manager = Caching.getCachingProvider().cacheManager
        manager.createCache("buckets", RedissonConfiguration.fromInstance(redissonClient, MutableConfiguration<String, Bucket>()))
        return manager
    }

    @Bean
    fun proxyManager(cacheManager: CacheManager): ProxyManager<String> =
        JCacheProxyManager(cacheManager.getCache("buckets"))
}