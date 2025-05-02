package com.bondidos.network.dto.peoples

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CrewMemberDto(
    val job: String?,
    val jobs: List<String>?,
    val person: PersonDto?,
)
