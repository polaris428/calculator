package com.example.calculator.dao

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.calculator.model.History

interface HistoryDao {
    @Query("SELECT * FROM history")
    fun  getAll(): List<History>

    @Insert
    fun insertHistory(history: History)

    @Query("DELETE FROM history")
    fun deleteAll()


}