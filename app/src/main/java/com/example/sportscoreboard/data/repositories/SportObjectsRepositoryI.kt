package com.example.sportscoreboard.data.repositories

import com.example.sportscoreboard.domain.SportObject
import com.example.sportscoreboard.domain.ResultState
import com.example.sportscoreboard.domain.filters.SportObjectTypeFilter
import kotlinx.coroutines.flow.Flow

interface SportObjectsRepositoryI {
    fun getFilteredSportObjects(text: String, filter: SportObjectTypeFilter): Flow<ResultState<List<SportObject>>>

    fun getFavoriteSportObjects(): Flow<ResultState<List<SportObject>>>

    suspend fun addToFavorite(sportObject: SportObject)

    suspend fun removeFromFavorite(sportObject: SportObject)
}