package com.example.biosignals.viewmodel.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.biosignals.database.UserDao
import com.example.biosignals.viewmodel.UserListViewModel

class UserListViewModelFactory(private val db: UserDao) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(UserListViewModel::class.java))
            return UserListViewModel(db) as T
        throw IllegalArgumentException("ERROR ARGUMENT TYPE")
    }
}