package com.example.sportscoreboard.data.remote.apiDescriptions

import com.example.sportscoreboard.domain.Participant
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ParticipantsApiDescription {
    @GET("search?type-ids=1,2,3&project-type-id=1&project-id=602&lang-id=1&sport-ids=1,2,3,4,5,6,7,8,9")
    suspend fun getFilteredParticipants(@Query("q") text: String): Response<List<Participant>>
}