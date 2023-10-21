package com.example.practiceapp.local

import androidx.room.Entity
import androidx.room.PrimaryKey

/*create entity for column in you able here there is 2 column*/
@Entity(tableName = "author")
data class Author(
    @PrimaryKey
    val id: String,
    val name: String,
)
