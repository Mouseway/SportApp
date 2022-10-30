package com.example.sportscoreboard.data.repositories

import com.example.sportscoreboard.domain.Entity
import com.example.sportscoreboard.domain.ResultState
import com.example.sportscoreboard.domain.filters.EntityFilter
import kotlinx.coroutines.flow.Flow

interface ParticipantsRepositoryI {
    fun getFilteredParticipants(text: String, filter: EntityFilter): Flow<ResultState<List<Entity>>>
}