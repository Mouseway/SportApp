package com.example.sportscoreboard.data.local.room.adapters

import com.example.sportscoreboard.data.local.room.enities.SportObjectEntity
import com.example.sportscoreboard.domain.Contest
import com.example.sportscoreboard.domain.Player
import com.example.sportscoreboard.domain.SportObject
import com.example.sportscoreboard.domain.Team
import com.example.sportscoreboard.domain.filters.SportObjectTypeFilter

class SportObjectRoomAdapter {
    fun fromRoomEntity(entity: SportObjectEntity): SportObject {
        val id = entity.id
        val name = entity.name
        val country = entity.country
        val sport = entity.sport
        val gender = entity.gender
        val image = entity.image


        return when(SportObjectTypeFilter.getById(entity.typeId)){
            SportObjectTypeFilter.CONTEST -> Contest(
                id = id,
                name = name,
                country = country,
                sport = sport,
                gender = gender,
                image = image,
                favorite = true
            )
            SportObjectTypeFilter.TEAM -> Team(
                id = id,
                name = name,
                country = country,
                sport = sport,
                gender = gender,
                image = image,
                favorite = true
            )
            SportObjectTypeFilter.SINGLE_PLAYER -> Player(
                id = id,
                name = name,
                country = country,
                sport = sport,
                gender = gender,
                image = image,
                favorite = true
            )
            else -> throw IllegalArgumentException("SportObjectType can be only contest, team or player!")
        }
    }

    fun toRoomEntity(sportObject: SportObject): SportObjectEntity {
        return SportObjectEntity(
            id = sportObject.id,
            name = sportObject.name,
            country = sportObject.country,
            sport = sportObject.sport,
            gender = sportObject.gender,
            image = sportObject.image,
            typeId = sportObject.filter.id
        )
    }
}