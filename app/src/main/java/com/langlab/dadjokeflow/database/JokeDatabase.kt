package com.langlab.dadjokeflow.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room.databaseBuilder
import androidx.room.RoomDatabase

@Database(entities = [JokeDTO::class], version = 1)
abstract class JokeDatabase : RoomDatabase() {
    abstract fun jokeDao(): JokeDao

    companion object {
        private var INSTANCE: JokeDatabase? = null
        private const val DBNAME = "dadjoke.db"

        fun getInstance(context: Context): JokeDatabase? {
            if (INSTANCE == null) {
                synchronized(JokeDatabase::class) {
                    INSTANCE = databaseBuilder(context, JokeDatabase::class.java, DBNAME).build()
                }
            }
            return INSTANCE
        }

        fun destroyInstance() {
            INSTANCE = null
        }
    }
}