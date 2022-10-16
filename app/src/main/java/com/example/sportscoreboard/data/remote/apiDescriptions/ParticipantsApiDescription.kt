package com.example.sportscoreboard.data.remote.apiDescriptions

import com.example.sportscoreboard.domain.Participant
import retrofit2.Response
import retrofit2.http.GET

interface ParticipantsApiDescription {
    @GET("search?type-ids=2,3&project-type-id=1&project-id=602&lang-id=1&q=dj&sport-ids=1,2,3,4,5,6,7,8,9")
    suspend fun getAllScores(): Response<List<Participant>>
}