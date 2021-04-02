package com.otus.social.security

import com.otus.social.service.ClientService
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Component

@Component
class AuthenticationProvider(private val customerService: ClientService) : AbstractUserDetailsAuthenticationProvider() {

    override fun additionalAuthenticationChecks(userDetails: UserDetails?, usernamePasswordAuthenticationToken: UsernamePasswordAuthenticationToken?) {
    }

    override fun retrieveUser(userName: String?, usernamePasswordAuthenticationToken: UsernamePasswordAuthenticationToken): UserDetails? {
        usernamePasswordAuthenticationToken.credentials?.let {
            val token = it.toString()
            return customerService.findByToken(token)
        }
        throw Exception("Token is not valid")
    }
}