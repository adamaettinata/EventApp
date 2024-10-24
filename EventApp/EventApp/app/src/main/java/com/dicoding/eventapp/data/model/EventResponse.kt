package com.dicoding.eventapp.data.model

import com.google.gson.annotations.SerializedName

data class EventResponse(
    @SerializedName("listEvents")
    val listEvents: List<Event> = listOf()
)
