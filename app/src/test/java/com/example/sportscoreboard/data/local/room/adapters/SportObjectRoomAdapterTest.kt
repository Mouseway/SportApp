package com.example.sportscoreboard.data.local.room.adapters

import com.example.sportscoreboard.data.local.room.enities.SportObjectEntity
import com.example.sportscoreboard.domain.Contest
import com.example.sportscoreboard.domain.Player
import com.example.sportscoreboard.domain.SportObject
import com.example.sportscoreboard.domain.Team
import com.google.common.truth.Truth
import org.junit.Before
import org.junit.Test

internal class SportObjectRoomAdapterTest {

    private lateinit var adapter: SportObjectRoomAdapter

    private val data: List<Pair<SportObject, SportObjectEntity>> = listOf(
        Pair(
            Team(
                id = "qwe",
                name = "Team Name",
                image = "path",
                sport = "Football",
                gender = "Man",
                country = "Albania",
                favorite = true
            ),
            SportObjectEntity(
                id = "qwe",
                name = "Team Name",
                typeId = 2,
                image = "path",
                sport = "Football",
                gender ="Man",
                country = "Albania"
            )
        ),
        Pair(
            Player(
                id = "abcd",
                name = "Player Name",
                image = "asdfghjk",
                sport = "Tennis",
                gender = "Woman",
                country = "Australia",
                favorite = true
            ),
            SportObjectEntity(
                id = "abcd",
                name = "Player Name",
                typeId = 3,
                image = "asdfghjk",
                sport = "Tennis",
                gender = "Woman",
                country = "Australia",
            )
        ),
        Pair(
            Contest(
                id = "zxc",
                name = "Contest Name",
                image = "pooodsdmk",
                gender = "Man",
                sport = "Shogi",
                country = "Japan",
                favorite = true
            ),
            SportObjectEntity(
                id = "zxc",
                name = "Contest Name",
                typeId = 1,
                image = "pooodsdmk",
                sport = "Shogi",
                gender = "Man",
                country = "Japan",
            )
        )
    )

    @Before
    fun init(){
        adapter = SportObjectRoomAdapter()
    }

    @Test
    fun fromRoomEntity() {
        data.forEach { (domain, room) ->
            val entity = adapter.fromRoomEntity(room)
            Truth.assertThat(entity).isEqualTo(domain)
        }
    }

    @Test
    fun toRoomEntity() {
        data.forEach { (domain, room) ->
            val entity = adapter.toRoomEntity(domain)
            Truth.assertThat(entity).isEqualTo(room)
        }
    }
}