package com.otus.social.dto.request

import com.otus.social.dto.CredentialDto
import com.otus.social.model.Gender
import io.swagger.annotations.ApiModelProperty
import java.time.LocalDate

class UserDto(
        @ApiModelProperty(notes = "For new user id is null")
        val id: Long? = null,
        val firstName: String,
        val lastName: String,
        val birthDay: LocalDate,
        val gender: Gender,
        val city: String,
        val interests: String = "",
        val credential: CredentialDto? = null
)