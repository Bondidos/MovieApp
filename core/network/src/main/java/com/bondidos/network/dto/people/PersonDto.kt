package com.bondidos.network.dto.people

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PersonDto(
    val name: String?,
    val ids: PeopleIdsDto?,
)
