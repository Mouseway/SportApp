package com.example.sportscoreboard.data.remote.adapters

import com.example.sportscoreboard.data.remote.entities.*
import com.example.sportscoreboard.domain.Contest
import com.example.sportscoreboard.domain.Player
import com.example.sportscoreboard.domain.SportObject
import com.example.sportscoreboard.domain.Team
import com.google.common.truth.Truth.assertThat
import org.junit.Before
import org.junit.Test

internal class SportObjectAdapterTest {

    private lateinit var adapter: SportObjectJsonAdapter

    private val data: List<Pair<SportObject, SportObjectJson>> = listOf(
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
            SportObjectJson(
            id = "qwe",
            name = "Team Name",
            type = TypeJson(
                id = 2,
                name = "Team"
            ),
            images = listOf(
                ImageJson("path", variantTypeId = 15)
            ),
            sport = SportJson("Football"),
            gender = GenderJson("Man"),
            defaultCountry = CountryJson("Albania"),
            favorite = true
        )),
        Pair(
            Player(
                id = "abcd",
                name = "Player Name",
                image = "asdfghjk",
                sport = "Tennis",
                gender = "Woman",
                country = "Australia",
                favorite = false
            ),
            SportObjectJson(
                id = "abcd",
                name = "Player Name",
                type = TypeJson(
                    id = 3,
                    name = "Player"
                ),
                images = listOf(
                    ImageJson("asdfghjk", variantTypeId = 15)
                ),
                sport = SportJson("Tennis"),
                gender = GenderJson("Woman"),
                defaultCountry = CountryJson("Australia"),
                favorite = false
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
            SportObjectJson(
                id = "zxc",
                name = "Contest Name",
                type = TypeJson(
                    id = 1,
                    name = "Contest"
                ),
                images = listOf(
                    ImageJson("pooodsdmk", variantTypeId = 15)
                ),
                sport = SportJson("Shogi"),
                gender = GenderJson("Man"),
                defaultCountry = CountryJson("Japan"),
                favorite = true
            )
        )
    )


    @Before
    fun init(){
        adapter = SportObjectJsonAdapter()
    }

    @Test
    fun fromJson() {
        data.forEach { (domain, json) ->
            val entity = adapter.fromJson(json)
            assertThat(entity).isEqualTo(domain)
        }
    }

    @Test
    fun toJson() {
        data.forEach { (domain, json) ->
            val entity = adapter.toJson(domain)
            assertThat(entity).isEqualTo(json)
        }
    }
}