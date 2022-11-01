package com.example.sportscoreboard.data.local.room.daos

import androidx.room.*
import com.example.sportscoreboard.data.local.room.enities.SportObjectEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface SportObjectDao {

    @Query("SELECT * FROM sportObjects")
    fun getAll(): Flow<List<SportObjectEntity>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(entity: SportObjectEntity)

    @Delete
    suspend fun delete(entity: SportObjectEntity)
}