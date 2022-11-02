package com.example.sportscoreboard.data.repositories

import com.example.sportscoreboard.data.local.room.adapters.SportObjectRoomAdapter
import com.example.sportscoreboard.data.local.room.daos.SportObjectDao
import com.example.sportscoreboard.data.remote.apiDescriptions.SportObjectApiDescription
import com.example.sportscoreboard.domain.ResultState
import com.example.sportscoreboard.domain.SportObject
import com.example.sportscoreboard.domain.filters.SportObjectTypeFilter
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class SportObjectsRepository(
    private val apiDescription: SportObjectApiDescription,
    private val dao: SportObjectDao,
    private val entityAdapter: SportObjectRoomAdapter) : SportObjectsRepositoryI {

    override fun getFilteredSportObjects(text: String, filter: SportObjectTypeFilter): Flow<ResultState<List<SportObject>>> {
        return flow {
            val typeFilter = getFilterIds(filter)

            emit(ResultState.Loading(isLoading = true))

            try {
                val response = apiDescription.getFilteredSportObjects(
                    text,
                    typeFilter.joinToString(separator = ",")
                )

                if (response.isSuccessful) {
                    emit(ResultState.Success(response.body()))
                } else {
                    val errorMessage = response.message()
                    emit(ResultState.Error(errorMessage))
                }

            }catch (e: Exception){
                emit(ResultState.Error(e.message ?: ""))
            }
        }
    }

    override fun getFavoriteSportObjects(): Flow<ResultState<List<SportObject>>> {
        return flow{
            emit(ResultState.Loading(isLoading = true))
            try{
                dao.getAll().collect { list ->
                    val data = list.map {
                        entityAdapter.fromRoomEntity(it)
                    }
                    emit(ResultState.Success(_data = data))
                }
            }catch (e: Exception){
                emit(ResultState.Error(e.message ?: ""))
            }
        }
    }

    override suspend fun addToFavorite(sportObject: SportObject){
        dao.insert(entity = entityAdapter.toRoomEntity(sportObject))
    }

    override suspend fun removeFromFavorite(sportObject: SportObject) {
        dao.delete(entityAdapter.toRoomEntity(sportObject))
    }

    private fun getFilterIds(filter: SportObjectTypeFilter): List<Int>{
        return (if(filter == SportObjectTypeFilter.ALL) {
            SportObjectTypeFilter
                .values()
                .filter { it != SportObjectTypeFilter.ALL }
        }else {
            listOf(filter)
        }).map { it.id }
    }
}

