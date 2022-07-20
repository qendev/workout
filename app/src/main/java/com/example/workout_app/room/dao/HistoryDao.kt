package com.example.workout_app.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.workout_app.room.entity.HistoryEntity
import kotlinx.coroutines.flow.Flow


@Dao
interface HistoryDao {

    @Insert
    suspend fun insert(historyEntity: HistoryEntity)

    @Query("SELECT * FROM `history-table`")
     fun fetchAllDates(): Flow<List<HistoryEntity>>
}