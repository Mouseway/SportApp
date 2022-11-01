package com.example.sportscoreboard.data.local.room.enities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "sportObjects")
data class SportObjectEntity(
    @PrimaryKey
    val id: String,
    val name: String,
    val sport: String,
    val image: String?,
    val country: String?,
    val gender: String?,
    val typeId: Int,
)