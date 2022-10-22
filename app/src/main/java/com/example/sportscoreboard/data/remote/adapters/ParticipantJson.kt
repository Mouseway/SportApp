package com.example.sportscoreboard.data.remote.adapters

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ParticipantJson(
    val name: String,
    val type: TypeJson?,
    val images: List<ImageJson>,
    val sport: SportJson,
    val gender: GenderJson?,
    val defaultCountry: CountryJson?
)

@JsonClass(generateAdapter = true)
class ImageJson(
    val path: String?,
    val variantTypeId: Int?
)

@JsonClass(generateAdapter = true)
class SportJson(
    val name: String?
)

@JsonClass(generateAdapter = true)
data class TypeJson(
    @Json(name = "id")
    val id: Int?,
    @Json(name = "name")
    val name: String?
)

@JsonClass(generateAdapter = true)
class GenderJson(
    val name: String?
)

@JsonClass(generateAdapter = true)
class CountryJson(
    val name: String?
)