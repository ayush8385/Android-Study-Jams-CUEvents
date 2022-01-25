package com.digitalhain.daipsisearch.Activities.Room

import androidx.lifecycle.LiveData
import com.ayush.hungreed.database.EventDao
import com.ayush.hungreed.database.EventEntity

class EventRepository(private val eventDao: EventDao) {
    val allEvents: LiveData<List<EventEntity>> = eventDao.getAllEvents()

    suspend fun insert(eventEntity: EventEntity){
       eventDao.insertEvent(eventEntity)
    }

    suspend fun delete(eventEntity: EventEntity){
        eventDao.deleteEvent(eventEntity)
    }

    suspend fun getEventById(id:String){
        eventDao.getEventsbyId(id)
    }


//    suspend fun delete(noteEntity: NoteEntity){
//        noteDao.delete(noteEntity)
//    }
}