package com.otus.social.dto

import springfox.documentation.annotations.ApiIgnore
import java.time.LocalDate

class FriendRequestDto(
        val userId: Long? = null,
        val fromUserId: Long? = null,
        val message: String,
        @ApiIgnore
        val requestDate: LocalDate = LocalDate.now()
)