package com.dicoding.eventapp.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.eventapp.data.model.Event
import com.dicoding.eventapp.data.repository.EventRepository
import kotlinx.coroutines.launch

class HomeViewModel(private val repository: EventRepository) : ViewModel() {
    private val _upcomingEvents = MutableLiveData<List<Event>>()
    val upcomingEvents: LiveData<List<Event>> = _upcomingEvents

    private val _completedEvents = MutableLiveData<List<Event>>()
    val completedEvents: LiveData<List<Event>> = _completedEvents

    private val _isLoadingUpcoming = MutableLiveData<Boolean>()
    val isLoadingUpcoming: LiveData<Boolean> = _isLoadingUpcoming

    private val _isLoadingCompleted = MutableLiveData<Boolean>()
    val isLoadingCompleted: LiveData<Boolean> = _isLoadingCompleted

    fun loadEvents() {
        loadUpcomingEvents()
        loadCompletedEvents()
    }

    private fun loadUpcomingEvents() {
        viewModelScope.launch {
            _isLoadingUpcoming.value = true
            try {
                val upcomingResult = repository.getActiveEvents()
                _upcomingEvents.value = upcomingResult.listEvents.take(5)
            } catch (e: Exception) {
                // Handle error
            } finally {
                _isLoadingUpcoming.value = false
            }
        }
    }

    private fun loadCompletedEvents() {
        viewModelScope.launch {
            _isLoadingCompleted.value = true
            try {
                val completedResult = repository.getCompletedEvents()
                _completedEvents.value = completedResult.listEvents.take(5)
            } catch (e: Exception) {
                // Handle error
            } finally {
                _isLoadingCompleted.value = false
            }
        }
    }

    fun searchEvents(query: String) {
        viewModelScope.launch {
            _isLoadingUpcoming.value = true
            _isLoadingCompleted.value = true
            try {
                val activeSearchResult = repository.searchEvents(1, query)
                _upcomingEvents.value = activeSearchResult.listEvents.take(5)

                val completedSearchResult = repository.searchEvents(0, query)
                _completedEvents.value = completedSearchResult.listEvents.take(5)
            } catch (e: Exception) {
                // Handle error
            } finally {
                _isLoadingUpcoming.value = false
                _isLoadingCompleted.value = false
            }
        }
    }
}