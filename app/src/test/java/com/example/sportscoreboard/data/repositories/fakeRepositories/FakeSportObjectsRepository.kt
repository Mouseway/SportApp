package com.example.sportscoreboard.data.repositories.fakeRepositories

import android.util.Log
import com.example.sportscoreboard.data.repositories.SportObjectsRepositoryI
import com.example.sportscoreboard.domain.*
import com.example.sportscoreboard.domain.filters.SportObjectTypeFilter
import kotlinx.coroutines.flow.*

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

    private val flowFavorite = MutableStateFlow<ResultState<List<SportObject>>>(ResultState.Success(getFavorite()))

    override fun getFilteredSportObjects(
        text: String,
        filter: SportObjectTypeFilter
    ): Flow<ResultState<List<SportObject>>> {
        return flow{
            emit(ResultState.Success(getFiltered(filter, text)))
        }
    }

    override fun getFavoriteSportObjects(): Flow<ResultState<List<SportObject>>> {
        return flowFavorite.asStateFlow()
    }

    override suspend fun addToFavorite(sportObject: SportObject) {
        val copy = sportObject.copy(favorite = true)
        data.remove(sportObject)
        data.add(copy)

        flowFavorite.update {
            ResultState.Success(getFavorite())
        }
    }

    override suspend fun removeFromFavorite(sportObject: SportObject) {
        data.remove(sportObject)
        val copy = sportObject.copy(favorite = false)
        data.add(copy)
        flowFavorite.update {
            ResultState.Success(getFavorite())
        }
    }

    private fun getFavorite(): List<SportObject>{
         return data.filter {
            it.favorite
        }
    }

    private fun getFiltered(filter: SportObjectTypeFilter, text: String): List<SportObject>{
        return data.filter {
            (filter == SportObjectTypeFilter.ALL ||
            it.filter == filter) && it.name.contains(text)
        }
    }
}