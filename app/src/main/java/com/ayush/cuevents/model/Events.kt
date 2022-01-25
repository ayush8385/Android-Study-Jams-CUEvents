package com.ayush.cuevents.model

import android.os.Parcel
import android.os.Parcelable

data class Events(
    val id:String,
    val name:String,
    val description:String,
    val detail:String,
    val image_url:String
)