package com.dicoding.eventapp.ui.completed

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.eventapp.data.model.Event
import com.dicoding.eventapp.data.repository.EventRepository
import kotlinx.coroutines.launch

class CompletedEventViewModel(private val repository: EventRepository) : ViewModel() {
    private val _events = MutableLiveData<List<Event>>()
    val events: LiveData<List<Event>> = _events

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun getCompletedEvents() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val response = repository.getCompletedEvents()
                _events.value = response.listEvents
                println("Completed events: ${response.listEvents}")
            } catch (e: Exception) {
                println("Error fetching completed events: ${e.message}")
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun searchCompletedEvents(query: String) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                _events.value = repository.searchEvents(0, query).listEvents
            } catch (e: Exception) {
                println("Error searching completed events: ${e.message}")
            } finally {
                _isLoading.value = false
            }
        }
    }
}