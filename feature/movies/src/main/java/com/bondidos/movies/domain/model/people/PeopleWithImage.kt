package com.bondidos.movies.domain.model.people

data class PeopleWithImage(
    val activity: String?,
    val activities: List<String>?,
    val person: Person?,
    val imageUrl: String,
)
