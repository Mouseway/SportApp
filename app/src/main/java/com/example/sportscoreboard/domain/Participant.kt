package com.example.sportscoreboard.domain
import com.example.sportscoreboard.R
import com.example.sportscoreboard.domain.filters.ParticipantFilter
import com.example.sportscoreboard.others.Constants

sealed class Participant(
    val name: String,
    val sport: String,
    val image: String?,
    val filter: ParticipantFilter,
    val gender: String?,
    val defaultImageSource: Int,
    val country: String?
){
    class Contest(_name: String, _sport: String, _image: String?, _gender: String?, _country: String?) : Participant(
        name = _name,
        sport = _sport,
        image = _image,
        filter = ParticipantFilter.CONTEST,
        defaultImageSource = R.drawable.contest,
        gender = _gender,
        country = _country,
    )

    class Team(_name: String, _sport: String, _image: String?, _gender: String?, _country: String?) : Participant(
        name = _name,
        sport = _sport,
        image = _image,
        filter = ParticipantFilter.TEAM,
        defaultImageSource = R.drawable.club,
        gender = _gender,
        country = _country
    )

    class Player(_name: String, _sport: String, _image: String?, _gender: String?, _country: String?) : Participant(
        name = _name,
        sport = _sport,
        image = _image,
        filter = ParticipantFilter.SINGLE_PLAYER,
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
}