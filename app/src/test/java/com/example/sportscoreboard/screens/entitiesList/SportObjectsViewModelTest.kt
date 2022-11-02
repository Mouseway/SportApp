package com.example.sportscoreboard.screens.entitiesList

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.sportscoreboard.data.repositories.fakeRepositories.FakeSportObjectsRepository
import com.example.sportscoreboard.domain.Player
import com.example.sportscoreboard.domain.SportObject
import com.example.sportscoreboard.domain.ResultState
import com.example.sportscoreboard.domain.Team
import com.example.sportscoreboard.domain.filters.SportObjectTypeFilter
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class SportObjectsViewModelTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private val dispatcher = StandardTestDispatcher()
    private lateinit var viewModel: SportObjectsViewModel

    @Before
    fun init(){
        Dispatchers.setMain(dispatcher)
        viewModel = SportObjectsViewModel(FakeSportObjectsRepository())
    }

    @After
    fun tearDown(){
        Dispatchers.resetMain()
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun setSearchedText() = runTest {
        val searchedText = "Petr"

        val values = mutableListOf<ResultState<List<SportObject>>>()

        viewModel.sportObjects.observeForever {
            values.add(it)
        }

        viewModel.setSearchedText(searchedText)
        viewModel.filterByText()

        dispatcher.scheduler.advanceUntilIdle()

        assertThat(viewModel.searchedText).isEqualTo(searchedText)
        assertThat(values.last().data).isNotNull()
        assertThat(values.last().data?.size).isEqualTo(2)

        values.last().data?.forEach {
            assertThat(it.name).contains(searchedText)
        }
    }

    @Test
    fun setParticipantTypeSO(){
        SportObjectTypeFilter.values().forEach { filter ->

            val values = mutableListOf<ResultState<List<SportObject>>>()

            viewModel.sportObjects.observeForever {
                values.add(it)
            }

            viewModel.setSearchedText("a")
            viewModel.setSportObjectFilter(filter)

            dispatcher.scheduler.advanceUntilIdle()

            assertThat(viewModel.sportObjectFilter.value).isEqualTo(filter)
            assertThat(values.last().data).isNotNull()

            if(filter != SportObjectTypeFilter.ALL){
                values.last().data?.forEach {
                    assertThat(it.filter).isEqualTo(filter)
                }
            }
        }
    }

    @Test
    fun setParticipantTypeFavorite(){
        SportObjectTypeFilter.values().forEach { filter ->

            val values = mutableListOf<ResultState<List<SportObject>>>()

            viewModel.favorite.observeForever {
                values.add(it)
            }

            viewModel.setSportObjectFilter(filter)

            dispatcher.scheduler.advanceUntilIdle()

            assertThat(viewModel.sportObjectFilter.value).isEqualTo(filter)
            assertThat(values.last().data).isNotNull()

            if(filter != SportObjectTypeFilter.ALL){
                values.last().data?.forEach {
                    assertThat(it.filter).isEqualTo(filter)
                }
            }
        }
    }

    @Test
    fun setParticipantTypeAndText(){
        val values = mutableListOf<ResultState<List<SportObject>>>()

        viewModel.sportObjects.observeForever {
            values.add(it)
        }

        val searchedText = "Petr"
        val filter = SportObjectTypeFilter.SINGLE_PLAYER

        viewModel.setSearchedText(searchedText)
        viewModel.setSportObjectFilter(filter)

        dispatcher.scheduler.advanceUntilIdle()

        assertThat(viewModel.searchedText).isEqualTo(searchedText)
        assertThat(viewModel.sportObjectFilter.value).isEqualTo(filter)
        assertThat(values.last().data).isNotNull()
        assertThat(values.last().data).isNotEmpty()

        values.last().data?.forEach {
            assertThat(it.filter).isEqualTo(filter)
            assertThat(it.name).contains(searchedText)
        }
    }

    @Test
    fun favoriteReturnOnlyFavoriteSO(){
        val values = mutableListOf<ResultState<List<SportObject>>>()

        viewModel.favorite.observeForever {
            values.add(it)
        }

        dispatcher.scheduler.advanceUntilIdle()

        assertThat(values.last()).isInstanceOf(ResultState.Success::class.java)
        assertThat(values.last().data).isNotNull()
        values.last().data?.forEach {
            assertThat(it.favorite).isTrue()
        }
    }

    @Test
    fun swapFavoriteToTrue(){

        val values = mutableListOf<ResultState<List<SportObject>>>()

        val so = Player("mnb", "Petra Kvitová", "Tennis", null, "Woman", "Czech republic", false)

        viewModel.favorite.observeForever {
            values.add(it)
        }

        viewModel.swapFavorite(so)

        dispatcher.scheduler.advanceUntilIdle()

        assertThat(values).isNotEmpty()
        assertThat(values.last()).isInstanceOf(ResultState.Success::class.java)
        assertThat(values.last().data).isNotNull()
        assertThat(values.last().data).contains(so.copy(favorite = true))
    }

    @Test
    fun swapFavoriteToFalse(){

        val values = mutableListOf<ResultState<List<SportObject>>>()

        val so = Team("abc", "FC Barcelona", "Football", null, "Man", "Spain", true)

        viewModel.favorite.observeForever {
            values.add(it)
        }

        viewModel.swapFavorite(so)

        dispatcher.scheduler.advanceUntilIdle()

        assertThat(values).isNotEmpty()
        assertThat(values.last()).isInstanceOf(ResultState.Success::class.java)
        assertThat(values.last().data).isNotNull()
        assertThat(values.last().data).doesNotContain(so)
        assertThat(values.last().data).doesNotContain(so.copy(favorite = false))
    }

    @Test
    fun showFavorite(){

        val values = mutableListOf<Boolean>()

        viewModel.showFavorite.observeForever {
            values.add(it)
        }

        viewModel.setSearchedText("aaa")
        viewModel.filterByText()

        dispatcher.scheduler.advanceUntilIdle()

        viewModel.setSearchedText("")
        viewModel.filterByText()

        dispatcher.scheduler.advanceUntilIdle()

        assertThat(values.size).isEqualTo(3)
        assertThat(values[0]).isTrue()
        assertThat(values[1]).isFalse()
        assertThat(values[2]).isTrue()

    }
}
