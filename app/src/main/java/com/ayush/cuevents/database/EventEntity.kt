package com.ayush.hungreed.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "events")
data class EventEntity(
    @PrimaryKey @ColumnInfo(name = "event_id") val eventId: String,
    @ColumnInfo(name = "event_name") val eventName : String,
    @ColumnInfo(name = "event_desc") val eventDesc: String,
    @ColumnInfo(name = "event_detail") val eventDetail: String,
    @ColumnInfo(name = "event_image") val eventImage: String
)
