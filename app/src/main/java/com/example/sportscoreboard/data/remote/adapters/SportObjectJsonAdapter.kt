package com.example.sportscoreboard.data.remote.adapters

import com.example.sportscoreboard.data.remote.entities.*
import com.example.sportscoreboard.domain.Contest
import com.example.sportscoreboard.domain.Player
import com.example.sportscoreboard.domain.SportObject
import com.example.sportscoreboard.domain.Team
import com.example.sportscoreboard.domain.filters.SportObjectTypeFilter
import com.squareup.moshi.FromJson
import com.squareup.moshi.ToJson

class SportObjectJsonAdapter {
    @FromJson
    fun fromJson(sportObject: SportObjectJson): SportObject{

        val id = sportObject.id
        val name = sportObject.name
        val sport = sportObject.sport.name ?: ""
        val image = sportObject.images.find { it.variantTypeId == 15 || it.variantTypeId == 19}?.path
        val gender = sportObject.gender?.name
        val country = sportObject.defaultCountry?.name
        val favorite = sportObject.favorite ?: false

        return when(sportObject.type?.id){
            SportObjectTypeFilter.CONTEST.id -> Contest(
                id = id,
                name = name,
                sport = sport,
                image = image,
                gender = gender,
                country = country,
                favorite = favorite
            )
            SportObjectTypeFilter.TEAM.id -> Team(
                id = id,
                name = name,
                sport = sport,
                image = image,
                gender = gender,
                country = country,
                favorite = favorite
            )
            else -> Player(
                id = id,
                name = name,
                sport = sport,
                image = image,
                gender = gender,
                country = country,
                favorite = favorite
            )
        }
    }

    @ToJson
    fun toJson(sportObject: SportObject): SportObjectJson {
        return SportObjectJson(
            id = sportObject.id,
            name = sportObject.name,
            type = TypeJson(sportObject.filter.id, sportObject.filter.title),
            images = listOf(ImageJson(sportObject.image, 15)),
            sport = SportJson(name = sportObject.sport),
            gender = GenderJson(sportObject.gender),
            defaultCountry = CountryJson(sportObject.country),
            favorite = sportObject.favorite
        )
    }
}