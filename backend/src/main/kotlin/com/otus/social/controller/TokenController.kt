package com.otus.social.controller

import com.otus.social.dto.LoginDto
import com.otus.social.service.ClientService
import com.otus.social.utils.handleResponse
import com.otus.social.utils.wrapResponse
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("token")
@CrossOrigin(origins = ["*"])
class TokenController(private val clientService: ClientService) {

    @PostMapping
    fun getToken(@RequestBody dto: LoginDto) =
            wrapResponse { clientService.getToken(dto.login, dto.password) }
}