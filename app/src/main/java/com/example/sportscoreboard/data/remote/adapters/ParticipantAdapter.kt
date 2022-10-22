package com.example.sportscoreboard.data.remote.adapters

import android.util.Log
import com.example.sportscoreboard.domain.Participant
import com.example.sportscoreboard.domain.filters.ParticipantFilter
import com.squareup.moshi.FromJson
import com.squareup.moshi.ToJson

class ParticipantAdapter {
    @FromJson
    fun fromJson(participant: ParticipantJson): Participant{

        val name = participant.name
        val sport = participant.sport.name ?: ""
        val image = participant.images.find { it.variantTypeId == 15 || it.variantTypeId == 19}?.path
        val gender = participant.gender?.name
        val country = participant.defaultCountry?.name

        return when(participant.type?.id){
            ParticipantFilter.CONTEST.id -> Participant.Contest(
                _name = name,
                _sport = sport,
                _image = image,
                _gender = gender,
                _country = country
            )
            ParticipantFilter.TEAM.id -> Participant.Team(
                _name = name,
                _sport = sport,
                _image = image,
                _gender = gender,
                _country = country
            )
            else ->Participant.Player(
                _name = name,
                _sport = sport,
                _image = image,
                _gender = gender,
                _country = country
            )
        }
    }

    @ToJson
    fun toJson(participant: Participant): ParticipantJson {
        return ParticipantJson(
            name = participant.name,
            type = TypeJson(participant.filter.id, participant.filter.title),
            images = listOf(ImageJson(participant.image, 15)),
            sport = SportJson(name = participant.name),
            gender = GenderJson(participant.gender),
            defaultCountry = CountryJson(participant.country)
        )
    }
}