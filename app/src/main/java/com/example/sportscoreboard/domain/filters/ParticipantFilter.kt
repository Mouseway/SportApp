package com.example.sportscoreboard.domain.filters

enum class ParticipantFilter(val id: Int, val title: String) {
    ALL(0, title = "Vše"), CONTEST(1, title = "Soutěž"), TEAM(2, title = "Tým"), SINGLE_PLAYER(3, title = "Hráč")
}