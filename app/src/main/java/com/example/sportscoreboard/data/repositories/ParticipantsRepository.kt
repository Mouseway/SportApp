package com.example.sportscoreboard.data.repositories

import com.example.sportscoreboard.data.remote.apiDescriptions.ParticipantsApiDescription
import com.example.sportscoreboard.domain.Entity
import com.example.sportscoreboard.domain.ResultState
import com.example.sportscoreboard.domain.filters.EntityFilter
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.net.SocketTimeoutException

class ParticipantsRepository(private val apiDescription: ParticipantsApiDescription) : ParticipantsRepositoryI {

    override fun getFilteredParticipants(text: String, filter: EntityFilter): Flow<ResultState<List<Entity>>> {
        return flow {

            val typeFilter = getFilterIds(filter)

            emit(ResultState.Loading(isLoading = true))

            try {
                val response = apiDescription.getFilteredParticipants(
                    text,
                    typeFilter.joinToString(separator = ",")
                )

                if (response.isSuccessful) {
                    emit(ResultState.Success(response.body()))
                } else {
                    val errorMessage = response.message()
                    emit(ResultState.Error(errorMessage, true))
                }

            }catch (e: SocketTimeoutException){
                emit(ResultState.Error("Failed to connect.", true))
            }
        }
    }

    private fun getFilterIds(filter: EntityFilter): List<Int>{
        return (if(filter == EntityFilter.ALL) {
            EntityFilter
                .values()
                .filter { it != EntityFilter.ALL }
        }else {
            listOf(filter)
        }).map { it.id }
    }
}

