package com.example.sportscoreboard.data.repositories

import com.example.sportscoreboard.data.remote.apiDescriptions.SportObjectApiDescription
import org.junit.Before
import org.koin.test.KoinTest
import org.mockito.Mockito.mock

internal class ScoreboardRepositoryTest : KoinTest{

    private lateinit var apiDescription: SportObjectApiDescription
    private lateinit var repository: SportObjectsRepository


    @Before
    fun init(){
        apiDescription = mock(SportObjectApiDescription::class.java)

    }
}