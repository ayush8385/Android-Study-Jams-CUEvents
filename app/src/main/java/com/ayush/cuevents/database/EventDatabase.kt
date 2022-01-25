package com.ayush.hungreed.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [EventEntity::class], version=1)
abstract class EventDatabase: RoomDatabase() {
    abstract fun eventDao():EventDao

    companion object{
        @Volatile
        private var INSTANCE: EventDatabase?=null

        fun getDatabase(context: Context): EventDatabase {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            return INSTANCE ?: synchronized(this){
                val instance=
                    Room.databaseBuilder(context.applicationContext, EventDatabase::class.java,"events-db").allowMainThreadQueries().build()
                INSTANCE =instance
                instance
            }
        }
    }
}