package com.dicoding.eventapp.data.api

import com.dicoding.eventapp.data.model.EventResponse
import com.dicoding.eventapp.data.model.DetailEventResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("events")
    suspend fun getEvents(@Query("active") active: Int): EventResponse

    @GET("events/{id}")
    fun getDetailEvent(@Path("id") id: String): Call<DetailEventResponse>

    @GET("events")
    suspend fun searchEvents(@Query("active") active: Int, @Query("q") query: String): EventResponse
}