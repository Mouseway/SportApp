package com.example.sportscoreboard.data.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.sportscoreboard.data.local.room.daos.SportObjectDao
import com.example.sportscoreboard.data.local.room.enities.SportObjectEntity

@Database(entities = [SportObjectEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract val sportObjectDao: SportObjectDao
}