package com.example.sportscoreboard.data.remote.adapters

import com.example.sportscoreboard.domain.Entity
import com.google.common.truth.Truth.assertThat
import org.junit.Before
import org.junit.Test

internal class EntityAdapterTest {

    lateinit var adapter: ParticipantAdapter

    private val data: List<Pair<Entity,ParticipantJson>> = listOf(
        Pair(
            Entity.Team(
                _name = "Team Name",
                _image = "path",
                _sport = "Football",
                _gender = "Man",
                _country = "Albania"
            ),
            ParticipantJson(
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
            defaultCountry = CountryJson("Albania")
        )),
        Pair(
            Entity.Player(
                _name = "Player Name",
                _image = "asdfghjk",
                _sport = "Tennis",
                _gender = "Woman",
                _country = "Australia"
            ),
            ParticipantJson(
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
                defaultCountry = CountryJson("Australia")
            )
        ),
        Pair(
            Entity.Contest(
                _name = "Contest Name",
                _image = "pooodsdmk",
                _sport = "Shogi",
                _gender = "Man",
                _country = "Japan"
            ),
            ParticipantJson(
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
                defaultCountry = CountryJson("Japan")
            )
        )
    )


    @Before
    fun init(){
        adapter = ParticipantAdapter()
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