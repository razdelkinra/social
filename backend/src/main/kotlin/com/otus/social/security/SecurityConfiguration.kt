package com.otus.social.security

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.builders.WebSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.web.AuthenticationEntryPoint
import org.springframework.security.web.authentication.AnonymousAuthenticationFilter
import org.springframework.security.web.authentication.HttpStatusEntryPoint
import org.springframework.security.web.util.matcher.AntPathRequestMatcher
import org.springframework.security.web.util.matcher.OrRequestMatcher
import org.springframework.security.web.util.matcher.RequestMatcher


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
class SecurityConfiguration(var provider: AuthenticationProvider) : WebSecurityConfigurerAdapter() {

    override fun configure(auth: AuthenticationManagerBuilder) {
        auth.authenticationProvider(provider)
    }

    override fun configure(webSecurity: WebSecurity) {
        webSecurity.ignoring().antMatchers("/token/**", "/registration/**")
    }

    override fun configure(http: HttpSecurity) {
        http.cors()
        http.authorizeRequests()
                .antMatchers("/token", "api/users/registration").permitAll()
                .and()
                .authenticationProvider(provider)
                .addFilterBefore(authenticationFilter(), AnonymousAuthenticationFilter::class.java)
                .authorizeRequests()
                .requestMatchers(PROTECTED_URLS)
                .authenticated()
                .and()
                .httpBasic().disable()
                .csrf().disable()
                .formLogin().disable()
                .logout().disable()
                .anonymous().disable()
                .exceptionHandling()
                .authenticationEntryPoint(forbiddenEntryPoint())
    }

    fun authenticationFilter(): AuthenticationFilter {
        val filter = AuthenticationFilter(PROTECTED_URLS)
        filter.setAuthenticationManager(authenticationManager())
        return filter
    }

    @Bean
    fun forbiddenEntryPoint(): AuthenticationEntryPoint {
        return HttpStatusEntryPoint(HttpStatus.FORBIDDEN)
    }


    companion object {
        val PROTECTED_URLS: RequestMatcher = OrRequestMatcher(
                AntPathRequestMatcher("/api/**")
        )
    }
}