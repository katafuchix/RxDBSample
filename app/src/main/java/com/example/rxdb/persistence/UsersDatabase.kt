package com.example.rxdb.persistence

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


/**
 * The Room database that contains the Users table
 */
@Database(entities = arrayOf(User::class), version = 1)
abstract class UsersDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao

    companion object {

        @Volatile private var INSTANCE: UsersDatabase? = null

        fun getInstance(context: Context): UsersDatabase =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: buildDatabase(context).also { INSTANCE = it }
            }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(context.applicationContext,
                UsersDatabase::class.java, "Sample.db")
                .build()
    }
}

/*
@Database(entities = [User::class], version = SampleDatabase.DATABASE_VERSION)
abstract class SampleDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao

    companion object {
        const val DATABASE_VERSION = 1
        private const val DATABASE_NAME = "sample.db"

        private var instance: SampleDatabase? = null

        fun init(context: Context) {
            Room.databaseBuilder(context, SampleDatabase::class.java, DATABASE_NAME)
                .build().also { instance = it }
        }

        fun getInstance() = instance
    }
}
 */