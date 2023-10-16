package com.example.practiceapp.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/*create entity for column in you able here there is 2 column*/
@Entity(tableName = "author")
data class Author(
    @PrimaryKey(autoGenerate = true)
//    val id: Int,
//    val name: String,
    @ColumnInfo(name = "id") val id: Int?,
    @ColumnInfo(name = "name") val name: String?
)
