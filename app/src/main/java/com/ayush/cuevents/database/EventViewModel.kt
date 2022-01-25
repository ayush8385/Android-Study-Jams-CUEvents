package com.digitalhain.daipsisearch.Activities.Room

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.ayush.hungreed.database.EventDatabase
import com.ayush.hungreed.database.EventEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class EventViewModel(application: Application): AndroidViewModel(application) {
    val allEvents: LiveData<List<EventEntity>>
    val repository: EventRepository

    init {
        val database= EventDatabase.getDatabase(application).eventDao()
        repository= EventRepository(database)
        allEvents=repository.allEvents
    }


    fun insertEvent(eventEntity: EventEntity) =viewModelScope.launch (Dispatchers.IO){
        repository.insert(eventEntity)
    }

    fun deleteEvent(eventEntity: EventEntity)=viewModelScope.launch (Dispatchers.IO){
        repository.delete(eventEntity)
    }

    suspend fun getEventsById(id:String){
        repository.getEventById(id)
    }
}