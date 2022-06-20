package com.example.biosignals.viewmodel.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.biosignals.viewmodel.HomeViewModel

class HomeViewModelFactory(private val path: String) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeViewModelFactory::class.java))
            return HomeViewModel(path) as T
        throw IllegalArgumentException("ERROR ARGUMENT TYPE")
    }
}