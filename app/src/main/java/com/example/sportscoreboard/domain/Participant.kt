package com.example.sportscoreboard.domain


import com.example.sportscoreboard.R

sealed class Participant(
    val name: String,
    val sport: String,
    val images: List<String>,
    val defaultImageSource: Int
){
    class Contest(_name: String, _sport: String, _images: List<String>) : Participant(
        name = _name,
        sport = _sport,
        images = _images,
        defaultImageSource = R.drawable.contest
    )

    class Team(_name: String, _sport: String, _images: List<String>) : Participant(
        name = _name,
        sport = _sport,
        images = _images,
        defaultImageSource = R.drawable.club
    )

    class Player(_name: String, _sport: String, _images: List<String>, private val gender: String) : Participant(
        name = _name,
        sport = _sport,
        images = _images,
        defaultImageSource = if(gender == "Women") R.drawable.player_woman else R.drawable.player_man
    )
}