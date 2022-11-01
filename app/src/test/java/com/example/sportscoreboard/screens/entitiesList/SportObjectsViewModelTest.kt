package com.example.sportscoreboard.screens.entitiesList


import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.sportscoreboard.data.repositories.fakeRepositories.FakeSportObjectsRepository
import com.example.sportscoreboard.domain.SportObject
import com.example.sportscoreboard.domain.ResultState
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

        dispatcher.scheduler.advanceUntilIdle()
        assertThat(viewModel.searchedText).isEqualTo(searchedText)
        assertThat(values[1].data).isNotNull()
        assertThat(values[1].data?.size).isEqualTo(2)

        values[1].data?.forEach {
            assertThat(it.name).contains(searchedText)
        }
    }

    @Test
    fun setParticipantType(){
        SportObjectTypeFilter.values().forEach { filter ->

            val values = mutableListOf<ResultState<List<SportObject>>>()

            viewModel.sportObjects.observeForever {
                values.add(it)
            }

            viewModel.setSearchedText("")
            viewModel.setSportObjectFilter(filter)

            dispatcher.scheduler.advanceUntilIdle()

            assertThat(viewModel.sportObjectFilter).isEqualTo(filter)
            assertThat(values[1].data).isNotNull()
            assertThat(values[1].data).isNotEmpty()

            if(filter != SportObjectTypeFilter.ALL){
                values[1].data?.forEach {
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
        assertThat(viewModel.sportObjectFilter).isEqualTo(filter)
        assertThat(values[2].data).isNotNull()
        assertThat(values[2].data).isNotEmpty()

        values[2].data?.forEach {
            assertThat(it.filter).isEqualTo(filter)
            assertThat(it.name).contains(searchedText)
        }
    }
}
