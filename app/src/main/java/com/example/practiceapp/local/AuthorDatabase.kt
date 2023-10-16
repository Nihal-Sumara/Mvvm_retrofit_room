package com.example.practiceapp.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import kotlin.concurrent.Volatile

/*mention all the table's you want to create in db here in array*/
@Database(entities = [Author::class], version = 1)
abstract class AuthorDatabase : RoomDatabase() {
    /*mention all fun for all the dao for different table you want to create*/
    abstract fun authorDao(): AuthorDao

    /*created singleton instance for database*/
    companion object {
        /*volatile is used for if there is value in this instance all the thread will be aware from its value*/
        @Volatile
        private var DATABASE_INSTANCE: AuthorDatabase? = null

        fun getDatabaseInstance(context: Context): AuthorDatabase {
            if (DATABASE_INSTANCE == null) {
                synchronized(this) {
                    DATABASE_INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        AuthorDatabase::class.java,
                        "AuthorDB"
                    ).build()
                }
            }
            return DATABASE_INSTANCE!!
        }
    }
}