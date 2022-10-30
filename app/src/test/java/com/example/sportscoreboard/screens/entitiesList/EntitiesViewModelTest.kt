package com.example.sportscoreboard.screens.entitiesList;


import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.sportscoreboard.data.repositories.fakeRepositories.FakeParticipantsRepository
import com.example.sportscoreboard.domain.Entity
import com.example.sportscoreboard.domain.ResultState
import com.example.sportscoreboard.domain.filters.EntityFilter
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
class EntitiesViewModelTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()


    private val dispatcher = StandardTestDispatcher()
    lateinit var viewModel: EntitiesViewModel

    @Before
    fun init(){
        Dispatchers.setMain(dispatcher)
        viewModel = EntitiesViewModel(FakeParticipantsRepository())
    }

    @After
    fun tearDown(){
        Dispatchers.resetMain()
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun setSearchedText() = runTest {
        val searchedText = "Petr"

        val values = mutableListOf<ResultState<List<Entity>>>()

        viewModel.scoreRecords.observeForever {
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
        EntityFilter.values().forEach { filter ->

            val values = mutableListOf<ResultState<List<Entity>>>()

            viewModel.scoreRecords.observeForever {
                values.add(it)
            }

            viewModel.setSearchedText("")
            viewModel.setParticipantType(filter)

            dispatcher.scheduler.advanceUntilIdle()

            assertThat(viewModel.participantType).isEqualTo(filter)
            assertThat(values[1].data).isNotNull()
            assertThat(values[1].data).isNotEmpty()

            if(filter != EntityFilter.ALL){
                values[1].data?.forEach {
                    assertThat(it.filter).isEqualTo(filter)
                }
            }
        }
    }

    @Test
    fun setParticipantTypeAndText(){
        val values = mutableListOf<ResultState<List<Entity>>>()

        viewModel.scoreRecords.observeForever {
            values.add(it)
        }

        val searchedText = "Petr"
        val filter = EntityFilter.SINGLE_PLAYER

        viewModel.setSearchedText(searchedText)
        viewModel.setParticipantType(filter)

        dispatcher.scheduler.advanceUntilIdle()

        assertThat(viewModel.searchedText).isEqualTo(searchedText)
        assertThat(viewModel.participantType).isEqualTo(filter)
        assertThat(values[2].data).isNotNull()
        assertThat(values[2].data).isNotEmpty()

        values[2].data?.forEach {
            assertThat(it.filter).isEqualTo(filter)
            assertThat(it.name).contains(searchedText)
        }
    }
}
