package com.example.sportscoreboard.data.remote.entities

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class SportObjectJson(
    val id: String,
    val name: String,
    val type: TypeJson?,
    val images: List<ImageJson>,
    val sport: SportJson,
    val gender: GenderJson?,
    val defaultCountry: CountryJson?,
    val favorite: Boolean? = null
)

@JsonClass(generateAdapter = true)
data class ImageJson(
    val path: String?,
    val variantTypeId: Int?
)

@JsonClass(generateAdapter = true)
data class SportJson(
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
data class GenderJson(
    val name: String?
)

@JsonClass(generateAdapter = true)
data class CountryJson(
    val name: String?
)