package com.example.sportscoreboard.data.local.room.daos

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.filters.SmallTest
import com.example.sportscoreboard.data.local.room.AppDatabase
import com.example.sportscoreboard.data.local.room.enities.SportObjectEntity
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test


@SmallTest
class SportObjectDaoTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var database: AppDatabase
    private lateinit var dao: SportObjectDao

    @Before
    fun init(){
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            AppDatabase::class.java
        ).allowMainThreadQueries().build()

        dao = database.sportObjectDao
    }

    @After
    fun tearDown(){
        database.close()
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun insertSOEntity() = runTest {

        val so = SportObjectEntity(
            "abc", "FC Barcelona", "Football", null, "Spain", "Man", 2
        )
        dao.insert(so)

        val items = dao.getAll().first()
        assertThat(items).contains(so)
    }


    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun deleteSOEntity() = runTest {
        val so = SportObjectEntity(
            "abc", "FC Barcelona", "Football", null, "Spain", "Man", 2
        )
        dao.insert(so)
        dao.delete(so)

        val items = dao.getAll().first()

        assertThat(items).doesNotContain(so)
    }


}