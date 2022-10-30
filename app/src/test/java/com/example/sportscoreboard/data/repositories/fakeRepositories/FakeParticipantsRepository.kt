package com.example.sportscoreboard.data.repositories.fakeRepositories

import com.example.sportscoreboard.data.repositories.ParticipantsRepositoryI
import com.example.sportscoreboard.domain.Entity
import com.example.sportscoreboard.domain.ResultState
import com.example.sportscoreboard.domain.filters.EntityFilter
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeParticipantsRepository : ParticipantsRepositoryI {

    private val data = listOf(
        Entity.Team("FC Barcelona", "Football", null, "Man", "Spain"),
        Entity.Player("Petra Kvitová", "Tennis", null, "Woman", "Czech republic"),
        Entity.Contest("U. S. Open", "Golf", null, "Man", null),
        Entity.Team("Chicago Blackhawks", "Hockey", null, null, null),
        Entity.Player("LeBron James", "Basketball", null, "Man", "USA"),
        Entity.Contest("NBA", "Basketball", null, "Man", "USA"),
        Entity.Player("Petr Čech", "Football", null, "Man", "Czech republic"),
    )

    override fun getFilteredParticipants(
        text: String,
        filter: EntityFilter
    ): Flow<ResultState<List<Entity>>> {
        return flow{
            val filtered = data.filter {
                (filter == EntityFilter.ALL ||
                it.filter == filter) && it.name.contains(text)
            }
            emit(
                ResultState.Success(_data = filtered)
            )
        }
    }
}