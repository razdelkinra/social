package com.otus.social.model

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

open class Failure(
        override val message: String,
        val code: Int = 500
) : RuntimeException()

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
class BadRequest(message: String) : Failure(message, 101)

@ResponseStatus(code = HttpStatus.CONFLICT, reason = "Not permitted operation")
class UserAlreadyExist(message: String) : Failure(message, 102)

@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "Not permitted operation")
class UserNotFound(message: String) : Failure(message, 103)

@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "Not permitted operation")
class InvalidRequest(message: String) : Failure(message, 104)
