package com.dicoding.eventapp.ui.active

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.eventapp.data.model.Event
import com.dicoding.eventapp.data.repository.EventRepository
import kotlinx.coroutines.launch

class ActiveEventViewModel(private val repository: EventRepository) : ViewModel() {
    private val _events = MutableLiveData<List<Event>>()
    val events: LiveData<List<Event>> = _events

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun getActiveEvents() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                _events.value = repository.getActiveEvents().listEvents
            } catch (e: Exception) {

            } finally {
                _isLoading.value = false
            }
        }
    }

    fun searchActiveEvents(query: String) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                _events.value = repository.searchEvents(1, query).listEvents
            } catch (e: Exception) {

            } finally {
                _isLoading.value = false
            }
        }
    }
}