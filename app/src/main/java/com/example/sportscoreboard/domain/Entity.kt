package com.example.sportscoreboard.domain
import com.example.sportscoreboard.R
import com.example.sportscoreboard.domain.filters.EntityFilter
import com.example.sportscoreboard.others.Constants

sealed class Entity(
    val name: String,
    val sport: String,
    val image: String?,
    val filter: EntityFilter,
    val gender: String?,
    val defaultImageSource: Int,
    val country: String?
){
    class Contest(_name: String, _sport: String, _image: String?, _gender: String?, _country: String?) : Entity(
        name = _name,
        sport = _sport,
        image = _image,
        filter = EntityFilter.CONTEST,
        defaultImageSource = R.drawable.contest,
        gender = _gender,
        country = _country,
    )

    class Team(_name: String, _sport: String, _image: String?, _gender: String?, _country: String?) : Entity(
        name = _name,
        sport = _sport,
        image = _image,
        filter = EntityFilter.TEAM,
        defaultImageSource = R.drawable.club,
        gender = _gender,
        country = _country
    )

    class Player(_name: String, _sport: String, _image: String?, _gender: String?, _country: String?) : Entity(
        name = _name,
        sport = _sport,
        image = _image,
        filter = EntityFilter.SINGLE_PLAYER,
        defaultImageSource = if(_gender == "Women") R.drawable.player_woman else R.drawable.player_man,
        gender = _gender,
        country = _country
    )

    fun getImagePath(): String {
        image?.let {
            return Constants.API_IMAGE_PATH + image
        }
        throw IllegalArgumentException()
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other?.javaClass != javaClass) return false

        other as Entity

        return (
            this.filter == other.filter &&
            this.name == other.name &&
            this.image == other.image &&
            this.country == other.country &&
            this.gender == other.gender &&
            this.sport == other.sport
        )
    }
}