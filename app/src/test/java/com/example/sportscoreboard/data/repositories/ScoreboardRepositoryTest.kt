package com.example.sportscoreboard.data.repositories

import com.example.sportscoreboard.data.local.room.adapters.SportObjectRoomAdapter
import com.example.sportscoreboard.data.local.room.daos.SportObjectDao
import com.example.sportscoreboard.data.local.room.enities.SportObjectEntity
import com.example.sportscoreboard.data.remote.apiDescriptions.SportObjectApiDescription
import com.example.sportscoreboard.domain.Player
import com.example.sportscoreboard.domain.ResultState
import com.example.sportscoreboard.domain.SportObject
import com.example.sportscoreboard.domain.Team
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.koin.test.KoinTest
import org.mockito.ArgumentMatchers.any
import org.mockito.Mockito.*

@OptIn(ExperimentalCoroutinesApi::class)
internal class ScoreboardRepositoryTest : KoinTest{

    private lateinit var apiDescription: SportObjectApiDescription
    private lateinit var dao: SportObjectDao
    private lateinit var entityAdapter: SportObjectRoomAdapter


    private lateinit var repository: SportObjectsRepository


    @Before
    fun init(){
        apiDescription = mock(SportObjectApiDescription::class.java)
        dao = mock(SportObjectDao::class.java)
        entityAdapter = SportObjectRoomAdapter()

        repository = SportObjectsRepository(apiDescription, dao, entityAdapter)
    }

    @Test
    fun getAllFavorite() = runTest {

        val data = listOf(
            SportObjectEntity(
                "abc", "FC Barcelona", "Football", null, "Spain", "Man", 2
            ),
            SportObjectEntity(
                "def", "Petra Kvitová", "Tennis", null, "Czech republic", "Woman", 3
            ),
        )

        val expected = data.map {
            entityAdapter.fromRoomEntity(it)
        }

        `when`(dao.getAll()).thenReturn(
            flow {
               emit(data)
            }
        )

        val results = mutableListOf<ResultState<List<SportObject>>>()

        repository.getFavoriteSportObjects().collect{
            results.add(it)
        }

        assertThat(results[0]).isInstanceOf(ResultState.Loading::class.java)
        assertThat(results[1]).isInstanceOf(ResultState.Success::class.java)
        assertThat(results[1].data).containsAtLeastElementsIn(expected)
    }

    @Test
    fun addToFavorite() = runTest {

        val sportObject = Player(
            id = "abcd",
            name = "Player Name",
            image = "asdfghjk",
            sport = "Tennis",
            gender = "Woman",
            country = "Australia",
            favorite = true
        )
        val entity = SportObjectEntity(
            id = "abcd",
            name = "Player Name",
            typeId = 3,
            image = "asdfghjk",
            sport = "Tennis",
            gender = "Woman",
            country = "Australia",
        )

        repository.addToFavorite(sportObject)

        verify(dao).insert(entity)
    }


    @Test
    fun removeFromFavorite() = runTest {
        val sportObject = Player(
            id = "abcd",
            name = "Player Name",
            image = "asdfghjk",
            sport = "Tennis",
            gender = "Woman",
            country = "Australia",
            favorite = true
        )
        val entity = SportObjectEntity(
            id = "abcd",
            name = "Player Name",
            typeId = 3,
            image = "asdfghjk",
            sport = "Tennis",
            gender = "Woman",
            country = "Australia",
        )

        repository.removeFromFavorite(sportObject)

        verify(dao).delete(entity)
    }
}