package xyz.maija.raincoat.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import xyz.maija.raincoat.data.dao.UserDao
import xyz.maija.raincoat.data.entities.User

@Database(entities = [User::class], version = 1, exportSchema = false)
abstract class AppDatabase:RoomDatabase() {

    // dif functions for each entity
    abstract fun userDao(): UserDao

    // expensive class so we only want a singleton of it
    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {

            val tempInstance = INSTANCE // create a new var so it's clear what we are doing
            if (tempInstance != null) {
                return tempInstance
            }
            // don't want a race condition to create at same time
            synchronized(this) {
                val instance = Room.databaseBuilder(context.applicationContext, AppDatabase::class.java, "customers").build()

                INSTANCE = instance
                return instance
            } // synchronized

        } // getDatabase
    } // companion object

} // AppDatabase