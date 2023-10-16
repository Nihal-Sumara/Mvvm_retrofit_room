package com.example.practiceapp.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface AuthorDao {
    @Insert
    suspend fun insert(author: Author)

    @Update
    suspend fun update(author: Author)

    @Delete
    suspend fun delete(author: Author)

    /*live data automatically performed in background with live data so no need of suspend
    * for room only*/
    @Query("SELECT * FROM author")
    fun select(): LiveData<List<Author>>
}