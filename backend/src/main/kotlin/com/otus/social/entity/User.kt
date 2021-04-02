package com.otus.social.entity

import java.time.LocalDate

class User(
        val firstName: String? = "",
        val lastName: String? = "",
        val birthDay: LocalDate? = null,
        val gender: String? = "",
        val city: String? = "",
        val interest: String = ""
) {
    var id: Long? = 0
}