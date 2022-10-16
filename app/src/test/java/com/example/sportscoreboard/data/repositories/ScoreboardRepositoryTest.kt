package com.example.sportscoreboard.data.repositories

import com.example.sportscoreboard.data.remote.apiDescriptions.ParticipantsApiDescription
import org.junit.Before
import org.koin.test.KoinTest
import org.mockito.Mockito.mock

internal class ScoreboardRepositoryTest : KoinTest{

    private lateinit var apiDescription: ParticipantsApiDescription
    private lateinit var repository: ParticipantsRepository


    @Before
    fun init(){
        apiDescription = mock(ParticipantsApiDescription::class.java)

    }
}