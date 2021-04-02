package com.otus.social.dto

import com.otus.social.model.Gender
import java.time.LocalDate

class UserDto(
        val id: Long? = null,
        val firstName: String,
        val lastName: String,
        val birthDay: LocalDate,
        val gender: Gender,
        val city: String,
        val interests: String = "",
        val credential: CredentialDto? = null
)