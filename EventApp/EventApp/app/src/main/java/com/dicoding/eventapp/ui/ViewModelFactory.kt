package com.dicoding.eventapp.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dicoding.eventapp.data.repository.EventRepository
import com.dicoding.eventapp.ui.active.ActiveEventViewModel
import com.dicoding.eventapp.ui.completed.CompletedEventViewModel
import com.dicoding.eventapp.ui.detail.DetailEventViewModel
import com.dicoding.eventapp.ui.home.HomeViewModel

class ViewModelFactory(private val repository: EventRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ActiveEventViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ActiveEventViewModel(repository) as T
        }
        if (modelClass.isAssignableFrom(CompletedEventViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return CompletedEventViewModel(repository) as T
        }
        if (modelClass.isAssignableFrom(DetailEventViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return DetailEventViewModel(repository) as T
        }
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return HomeViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}