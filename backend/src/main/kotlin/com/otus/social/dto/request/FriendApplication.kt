package com.otus.social.dto.request

import springfox.documentation.annotations.ApiIgnore
import java.time.LocalDate

class FriendApplication(
        val id: Long? = null,
        val firstName: String,
        val lastName: String,
        val message: String,
        @ApiIgnore
        val requestDate: LocalDate = LocalDate.now()

)