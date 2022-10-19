package com.example.sportscoreboard.data.repositories

import android.util.Log
import com.example.sportscoreboard.domain.ResultState
import com.example.sportscoreboard.data.remote.apiDescriptions.ParticipantsApiDescription
import com.example.sportscoreboard.domain.Participant
import com.example.sportscoreboard.domain.filters.ParticipantFilter
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class ParticipantsRepository(private val apiDescription: ParticipantsApiDescription) {

    fun getFilteredParticipants(text: String, filter: ParticipantFilter): Flow<ResultState<List<Participant>>> {
        return flow {

            val typeFilter = (if(filter == ParticipantFilter.ALL)
                                ParticipantFilter
                                    .values()
                                    .filter { it != ParticipantFilter.ALL }
                             else
                                listOf(filter)).map { it.id }


            emit(ResultState.Loading(isLoading = true))
            val response = apiDescription.getFilteredParticipants(text, typeFilter.joinToString(separator = ","))
            if(response.isSuccessful){
                emit(ResultState.Success(response.body()))
            }else{
                val errorMessage = response.message()
                emit(ResultState.Error(errorMessage, true))
            }
        }
    }
}