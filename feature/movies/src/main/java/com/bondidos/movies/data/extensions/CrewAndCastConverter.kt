package com.bondidos.movies.data.extensions

import com.bondidos.movies.domain.model.people.CastAndCrewImages
import com.bondidos.movies.domain.model.people.CrewAndCastMember
import com.bondidos.movies.domain.model.people.PeopleIds
import com.bondidos.movies.domain.model.people.Person
import com.bondidos.network.dto.people.CastAndCrewImagesDto
import com.bondidos.network.dto.people.CrewAndCastDto
import com.bondidos.network.dto.people.PeopleIdsDto
import com.bondidos.network.dto.people.PersonDto

private const val IMAGE_URL = "https://image.tmdb.org/t/p/w185"

fun CrewAndCastDto.toCrewAndCastMemberList(): List<CrewAndCastMember> {
    if (cast != null && crew != null) {
        val result = cast?.map {
            CrewAndCastMember(
                activity = it.character,
                activities = it.characters,
                person = it.person?.toPerson()
            )
        }?.toMutableList() ?: mutableListOf()

        crew?.values?.forEach { list ->
            val crewList = list?.map { element ->
                CrewAndCastMember(
                    activity = element.job,
                    activities = element.jobs,
                    person = element.person?.toPerson(),
                )
            }
            result + crewList
        }
        return result.toList()
    }
    return emptyList()
}

fun CastAndCrewImagesDto.toCastAndCrewImages() =
    CastAndCrewImages(IMAGE_URL + profiles?.firstOrNull()?.filePath)

fun PersonDto.toPerson() = Person(name, ids?.toPeopleIds())

fun PeopleIdsDto.toPeopleIds() = PeopleIds(trakt, slug, imdb, tmdb, tvrage)
