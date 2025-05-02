package com.bondidos.network.dto.peoples

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PersonDto(
    val name: String?,
    val ids: PeopleIdsDto?,
)
