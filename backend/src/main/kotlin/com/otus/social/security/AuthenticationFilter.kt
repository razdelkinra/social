package com.otus.social.security

import org.apache.commons.lang3.StringUtils
import org.springframework.http.HttpHeaders
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter
import org.springframework.security.web.util.matcher.RequestMatcher
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class AuthenticationFilter(requiresAuthenticationRequestMatcher: RequestMatcher) : AbstractAuthenticationProcessingFilter(requiresAuthenticationRequestMatcher) {

    override fun attemptAuthentication(httpServletRequest: HttpServletRequest, httpServletResponse: HttpServletResponse?): Authentication? {
        var token = if (StringUtils.isNotEmpty(httpServletRequest.getHeader(HttpHeaders.AUTHORIZATION))) httpServletRequest.getHeader(HttpHeaders.AUTHORIZATION) else ""
        return if (token.isBlank()) {
            throw BadCredentialsException("Token is not valid")
        } else {
            token = StringUtils.removeStart(token, "Bearer").trim()
            val requestAuthentication = UsernamePasswordAuthenticationToken(token, token)
            //requestAuthentication.details
            authenticationManager.authenticate(requestAuthentication)
        }
    }

    override fun successfulAuthentication(request: HttpServletRequest?, response: HttpServletResponse?, chain: FilterChain, authResult: Authentication?) {
        SecurityContextHolder.getContext().authentication = authResult
        chain.doFilter(request, response)
    }
}