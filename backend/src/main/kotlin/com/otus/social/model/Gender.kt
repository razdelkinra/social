package com.otus.social.model

import com.fasterxml.jackson.annotation.JsonProperty

enum class Gender {
    @JsonProperty("male")
    MALE,

    @JsonProperty("female")
    FEMALE
}