package com.bondidos.network.dto.people

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CrewMemberDto(
    val job: String?,
    val jobs: List<String>?,
    val person: PersonDto?,
)
