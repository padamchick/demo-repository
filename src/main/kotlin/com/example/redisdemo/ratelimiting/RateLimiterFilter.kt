package com.example.redisdemo.ratelimiting

import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter

@Component
class RateLimiterFilter(
    private val rateLimiter: RateLimiter
) : OncePerRequestFilter() {
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        rateLimiter.runThrottle(request, response)
        filterChain.doFilter(request, response)
    }
}