package com.otus.social.controller

import arrow.core.Either
import com.otus.social.dto.LoginDto
import com.otus.social.service.ClientService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("token")
class TokenController(private val clientService: ClientService) {

    @PostMapping
    fun getToken(@RequestBody dto: LoginDto) = when (val token = clientService.getToken(dto.login, dto.password)) {
        is Either.Left -> when (token.a) {
            else -> ResponseEntity.status(HttpStatus.BAD_REQUEST).body(token.a.message)
        }
        is Either.Right -> ResponseEntity.ok(token.b)
    }
}