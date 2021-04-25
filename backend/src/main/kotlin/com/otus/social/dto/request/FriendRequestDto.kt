package com.otus.social.dto.request

import springfox.documentation.annotations.ApiIgnore
import java.time.LocalDate

class FriendRequestDto(
        val userId: Long,
        val fromUserId: Long,
        val message: String,
        @ApiIgnore
        val requestDate: LocalDate = LocalDate.now()
)