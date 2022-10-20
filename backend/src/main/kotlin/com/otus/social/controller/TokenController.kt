package com.otus.social.controller

import com.otus.social.dto.LoginDto
import com.otus.social.service.ClientService
import com.otus.social.utils.saveResponse
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("token")
@CrossOrigin(origins = ["*"])
class TokenController(private val clientService: ClientService) {

    @PostMapping
    fun getToken(@RequestBody dto: LoginDto) =
            saveResponse { clientService.generateToken(dto.login, dto.password) }
}