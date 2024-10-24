package com.dicoding.eventapp.data.repository

import com.dicoding.eventapp.data.api.ApiService
import com.dicoding.eventapp.data.model.DetailEventResponse
import retrofit2.Call

class EventRepository(private val apiService: ApiService) {
    suspend fun getActiveEvents() = apiService.getEvents(1)
    suspend fun getCompletedEvents() = apiService.getEvents(0)
    fun getEventDetail(id: String): Call<DetailEventResponse> = apiService.getDetailEvent(id)
    suspend fun searchEvents(active: Int, query: String) = apiService.searchEvents(active, query)
}