package com.otus.social.dto

class ClientDto(
        var userId: Long,
        var login: String,
        var password: String,
        var token: String?
)