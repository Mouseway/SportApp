package com.example.sportscoreboard.data.remote.adapters

import com.example.sportscoreboard.domain.Entity
import com.example.sportscoreboard.domain.filters.EntityFilter
import com.squareup.moshi.FromJson
import com.squareup.moshi.ToJson

class ParticipantAdapter {
    @FromJson
    fun fromJson(participant: ParticipantJson): Entity{

        val name = participant.name
        val sport = participant.sport.name ?: ""
        val image = participant.images.find { it.variantTypeId == 15 || it.variantTypeId == 19}?.path
        val gender = participant.gender?.name
        val country = participant.defaultCountry?.name

        return when(participant.type?.id){
            EntityFilter.CONTEST.id -> Entity.Contest(
                _name = name,
                _sport = sport,
                _image = image,
                _gender = gender,
                _country = country
            )
            EntityFilter.TEAM.id -> Entity.Team(
                _name = name,
                _sport = sport,
                _image = image,
                _gender = gender,
                _country = country
            )
            else ->Entity.Player(
                _name = name,
                _sport = sport,
                _image = image,
                _gender = gender,
                _country = country
            )
        }
    }

    @ToJson
    fun toJson(entity: Entity): ParticipantJson {
        return ParticipantJson(
            name = entity.name,
            type = TypeJson(entity.filter.id, entity.filter.title),
            images = listOf(ImageJson(entity.image, 15)),
            sport = SportJson(name = entity.sport),
            gender = GenderJson(entity.gender),
            defaultCountry = CountryJson(entity.country)
        )
    }
}