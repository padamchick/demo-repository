package com.example.redisdemo.config

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonTypeInfo
import com.fasterxml.jackson.annotation.JsonTypeInfo.As
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.ObjectMapper.DefaultTyping.EVERYTHING
import com.fasterxml.jackson.databind.ObjectMapper.DefaultTyping.NON_FINAL
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.databind.jsontype.BasicPolymorphicTypeValidator
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.KotlinFeature
import com.fasterxml.jackson.module.kotlin.KotlinModule
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule
import org.springframework.boot.autoconfigure.cache.RedisCacheManagerBuilderCustomizer
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.cache.RedisCacheConfiguration
import org.springframework.data.redis.connection.RedisConnectionFactory
import org.springframework.data.redis.connection.RedisStandaloneConfiguration
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer
import org.springframework.data.redis.serializer.RedisSerializationContext.SerializationPair
import org.springframework.data.redis.serializer.RedisSerializer
import org.springframework.data.redis.serializer.StringRedisSerializer
import java.time.Duration

@Configuration
class RedisConfig {
    @Bean
    fun cacheConfiguration(): RedisCacheConfiguration {
        return RedisCacheConfiguration.defaultCacheConfig()
            .entryTtl(Duration.ofMinutes(60))
            .disableCachingNullValues()
            .serializeValuesWith(
                SerializationPair.fromSerializer(GenericJackson2JsonRedisSerializer())
            )
    }

    @Bean
    fun redisConnectionFactory(): LettuceConnectionFactory =
        LettuceConnectionFactory(RedisStandaloneConfiguration("localhost", 6379))

    @Bean
    fun genericJackson2JsonRedisSerializer(): GenericJackson2JsonRedisSerializer {
        val mapper = ObjectMapper()
            .registerModule(ParameterNamesModule(JsonCreator.Mode.DEFAULT))
            .registerModule(Jdk8Module())
            .registerKotlinModule()
            .registerModule(JavaTimeModule())
            .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)

        mapper.activateDefaultTyping(mapper.polymorphicTypeValidator, EVERYTHING, As.PROPERTY)
        return GenericJackson2JsonRedisSerializer(mapper)
    }

    @Bean
    fun redisTemplate(redisConnectionFactory: RedisConnectionFactory): RedisTemplate<String, String> {
        val stringRedisSerializer: RedisSerializer<String> = StringRedisSerializer()
        return RedisTemplate<String, String>().also {
            it.setConnectionFactory(redisConnectionFactory)
            it.keySerializer = stringRedisSerializer
            it.valueSerializer = genericJackson2JsonRedisSerializer()
            it.hashKeySerializer = stringRedisSerializer
            it.hashValueSerializer = genericJackson2JsonRedisSerializer()
        }
    }



    @Bean
    fun redisCacheManagerBuilderCustomizer(): RedisCacheManagerBuilderCustomizer =
        RedisCacheManagerBuilderCustomizer { builder ->
            builder.withCacheConfiguration(
                "studentCache",
                RedisCacheConfiguration.defaultCacheConfig()
                    .entryTtl(Duration.ofMinutes(1))
                    .disableCachingNullValues()
                    .serializeValuesWith(
                        SerializationPair.fromSerializer(GenericJackson2JsonRedisSerializer())
                    )
            )
        }
}