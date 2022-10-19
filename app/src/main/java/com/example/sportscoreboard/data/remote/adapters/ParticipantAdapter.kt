package com.example.sportscoreboard.data.remote.adapters

import com.example.sportscoreboard.domain.Participant
import com.example.sportscoreboard.domain.filters.ParticipantFilter
import com.squareup.moshi.FromJson
import com.squareup.moshi.ToJson

class ParticipantAdapter {
    @FromJson
    fun fromJson(participant: ParticipantJson): Participant{
        return when(participant.type?.id){
            ParticipantFilter.CONTEST.id -> Participant.Contest(
                _name = participant.name,
                _sport = participant.sport.name ?: "",
                _images = participant.images.mapNotNull { it.path }
            )
            ParticipantFilter.TEAM.id -> Participant.Team(
                _name = participant.name,
                _sport = participant.sport.name ?: "",
                _images = participant.images.mapNotNull { it.path }
            )
            else ->Participant.Player(
                _name = participant.name,
                _sport = participant.sport.name ?: "",
                _images = participant.images.mapNotNull { it.path },
                gender = participant.gender?.name ?: ""
            )
        }
    }

    @ToJson
    fun toJson(participant: Participant): ParticipantJson {
        return ParticipantJson(
            name = participant.name,
            type = null,
            images = participant.images.map { ImageJson(it) },
            sport = SportJson(name = participant.name),
            gender = null
        )
    }
}