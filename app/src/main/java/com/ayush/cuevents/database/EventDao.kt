package com.ayush.hungreed.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface EventDao {
    @Insert
    fun insertEvent(eventEntity: EventEntity)

    @Delete
    fun deleteEvent(eventEntity: EventEntity)

    @Query("SELECT * FROM events")
    fun getAllEvents():LiveData<List<EventEntity>>

    @Query("SELECT * FROM events where event_id=:eventId")
    fun getEventsbyId(eventId:String):EventEntity
}