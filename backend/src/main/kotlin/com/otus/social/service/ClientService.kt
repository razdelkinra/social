package com.otus.social.service

import arrow.core.Either
import com.otus.social.model.Failure
import org.springframework.security.core.userdetails.UserDetails

interface ClientService {
    fun getToken(username: String, password: String): Either<Failure, String>
    fun findByToken(token: String): UserDetails?
}