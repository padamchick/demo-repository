package com.example.redisdemo.ratelimiting

import io.github.bucket4j.Bandwidth
import io.github.bucket4j.Bucket
import io.github.bucket4j.BucketConfiguration
import io.github.bucket4j.Refill
import io.github.bucket4j.distributed.proxy.ProxyManager
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Component
import java.time.Duration

private const val USER_HEADER = "USER"
private const val DEFAULT_CONSUME_TOKEN = 1L
private const val BILLION = 1_000_000_000


@Component
class RateLimiter(
    private val proxyManager: ProxyManager<String>
) {
    fun runThrottle(request: HttpServletRequest, response: HttpServletResponse) {
        val path = request.servletPath
        val userId = request.getHeader(USER_HEADER).toInt()

        val bucket = resolveBucket(path, userId)

        val probe = bucket.tryConsumeAndReturnRemaining(DEFAULT_CONSUME_TOKEN)
        val refillIn = probe.nanosToWaitForRefill
        val tokensLeft = probe.remainingTokens

        if(!probe.isConsumed) {
            response.setHeader("REFILL-IN", "${refillIn / BILLION}s")
            response.sendError(HttpStatus.TOO_MANY_REQUESTS.value(), "Too many requests")
        } else {
            response.setHeader("REFILL-IN", refillIn.toString())
            response.setHeader("TOKENS-LEFT", tokensLeft.toString())
        }
    }

    private fun resolveBucket(path: String, userId: Int): Bucket {
        return proxyManager.builder().build(composeBucketKey(path, userId), configSupplierForUser)
    }

    private fun composeBucketKey(path: String, userId: Int) = "$path:$userId:3:30"

    private val configSupplierForUser = {
        val refill = Refill.intervally(3, Duration.ofSeconds(30))
        val bandwidth = Bandwidth.classic(3, refill)
        BucketConfiguration.builder()
            .addLimit(bandwidth)
            .build()
    }
}