package com.example.sportscoreboard.data.repositories.fakeRepositories

import com.example.sportscoreboard.data.repositories.SportObjectsRepositoryI
import com.example.sportscoreboard.domain.*
import com.example.sportscoreboard.domain.filters.SportObjectTypeFilter
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeSportObjectsRepository : SportObjectsRepositoryI {

    private val data = mutableListOf(
        Team("abc", "FC Barcelona", "Football", null, "Man", "Spain", false),
        Player("mnb", "Petra Kvitová", "Tennis", null, "Woman", "Czech republic", false),
        Contest("poiy", "U. S. Open", "Golf", null, "Man", null, true),
        Team("fghj", "Chicago Blackhawks", "Hockey", null, null, null, true),
        Player("pokfd", "LeBron James", "Basketball", null, "Man", "USA", true),
        Contest("yuk", "NBA", "Basketball", null, "Man", "USA", false),
        Player("qwe", "Petr Čech", "Football", null, "Man", "Czech republic", true),
    )

    override fun getFilteredSportObjects(
        text: String,
        filter: SportObjectTypeFilter
    ): Flow<ResultState<List<SportObject>>> {
        return flow{
            val filtered = data.filter {
                (filter == SportObjectTypeFilter.ALL ||
                it.filter == filter) && it.name.contains(text)
            }
            emit(
                ResultState.Success(_data = filtered)
            )
        }
    }

    override fun getFavoriteSportObjects(): Flow<ResultState<List<SportObject>>> {
        return flow {
            val favorite = data.filter {
                it.favorite
            }
            emit(ResultState.Success(favorite))
        }
    }

    override suspend fun addToFavorite(sportObject: SportObject) {
        data.remove(sportObject)
        val copy = sportObject.copy(favorite = true)
        data.add(copy)
    }

    override suspend fun removeFromFavorite(sportObject: SportObject) {
        data.remove(sportObject)
        val copy = sportObject.copy(favorite = false)
        data.add(copy)
    }
}