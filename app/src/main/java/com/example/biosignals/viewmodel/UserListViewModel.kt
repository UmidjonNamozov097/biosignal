package com.example.biosignals.viewmodel

import androidx.lifecycle.ViewModel
import com.example.biosignals.database.UserDao

class UserListViewModel(val db: UserDao) : ViewModel() {
    var dataList = db.getUsers()
}